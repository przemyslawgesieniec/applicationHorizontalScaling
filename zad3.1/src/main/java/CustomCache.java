import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomCache<K, V> {


    Map<K,V> cache = new ConcurrentHashMap<>();

    public void put(K key, V value){
        synchronized (cache){
            cache.put(key,value);
        }
    }

    public V get(K key){
        synchronized (cache){
            return cache.get(key);
        }
    }

    public int size(){
        synchronized (cache){
            return cache.size();
        }
    }

    public void remove(K key){
        synchronized (cache){
            cache.remove(key);
        }
    }

    public void clear(){
        synchronized (cache){
            cache.clear();
        }
    }

}
