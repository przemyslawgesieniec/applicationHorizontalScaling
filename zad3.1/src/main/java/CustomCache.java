import java.lang.ref.SoftReference;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CustomCache<K, V> {

    private Map<K, CacheValueObject> cache;
    private int maxSize;
    private long objectTTL;

    protected class CacheValueObject implements Comparable<CacheValueObject> {

        protected long lastAccess;
        protected V value;

        protected CacheValueObject(V value) {
            this.lastAccess = System.currentTimeMillis();
            this.value = value;
        }

        @Override
        public int compareTo(CacheValueObject o) {
            return (int) (this.lastAccess - o.lastAccess);
        }
    }

    public CustomCache() {
        this(Integer.MAX_VALUE, Long.MAX_VALUE);
    }

    public CustomCache(int size) {
        this(size, Long.MAX_VALUE);
    }

    public CustomCache(long objectTTL) {
        this(Integer.MAX_VALUE, objectTTL);
    }

    public CustomCache(int maxSize, long objectTTL) {
        this.cache = new ConcurrentHashMap<>();
        this.maxSize = maxSize;
        this.objectTTL = objectTTL;
        cleaningDemon();
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
    }

    public synchronized void put(K key, V value) {

        if (cache.size() < maxSize) {
            cache.put(key, new CacheValueObject(value));
        } else {
            cache.entrySet()
                    .stream()
                    .min(Comparator.comparing(Map.Entry::getValue))
                    .ifPresent(e -> cache.remove(e.getKey()));

            cache.put(key, new CacheValueObject(value));
        }
    }

    public V get(K key) {

        CacheValueObject cacheValueObject = cache.get(key);
        if (cacheValueObject == null) {
            return null;
        } else {
            cacheValueObject.lastAccess = System.currentTimeMillis();
            return cache.get(key).value;
        }
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public synchronized void setCapacity(int capacity) {
        maxSize = capacity;
    }

    public synchronized void setTimeout(int timeout) {
        objectTTL = timeout;
    }

    private void cleaningDemon() {

        Thread cleaningDemon = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    final long objectTTLms = objectTTL * 1000;
                    Thread.sleep(objectTTL * 1000);
                    cache.entrySet().removeIf(e -> System.currentTimeMillis() - e.getValue().lastAccess < objectTTLms);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleaningDemon.setDaemon(true);
        cleaningDemon.start();
    }
}
