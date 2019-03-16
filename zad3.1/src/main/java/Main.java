public class Main {

    public static void main(String[] args) {

        CustomCache<Integer, String> customCache = new CustomCache<>(90, 20);

        customCache.put(1, "one");
        customCache.put(2, "two");
        customCache.put(3, "three");
        customCache.put(4, "four");
        customCache.put(5, "five");
        customCache.put(6, "six");
        customCache.put(7, "seven");
        customCache.put(8, "eight");
        customCache.put(9, "nine");

        Thread t1 = new Thread(() -> {
            customCache.remove(1);
            customCache.put(67, "sixtyseven");
            customCache.put(69, "sixtynine");
            customCache.remove(5);
        });

        Thread t2 = new Thread(() -> {
            customCache.setCapacity(80);
            customCache.setTimeout(80);
            customCache.put(90, "jkhvjhv");
            customCache.put(60, "jkhvjhv");
            customCache.remove(2);

        });

        t1.start();
        t2.start();
        customCache.forEach((k, v) -> System.out.print(k + ", " + v + " | "));

//        while (true) {
//            customCache.forEach((k, v) -> System.out.print(k + ", " + v + " | "));
//            System.out.println();
//        }

    }
}
