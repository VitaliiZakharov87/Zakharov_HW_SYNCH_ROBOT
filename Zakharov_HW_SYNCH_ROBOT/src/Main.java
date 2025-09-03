import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        int countThreads = 1000;
        for (int i = 0; i < countThreads; i++) {
            Thread thread = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                Integer RFreq = (int) route.chars().filter(ch -> ch == 'R').count();
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(RFreq)) sizeToFreq.put(RFreq, sizeToFreq.get(RFreq) + 1);
                    else sizeToFreq.put(RFreq, 1);
                }
                System.out.println("Количесто поворотов направо: " + RFreq);
            });
            threadList.add(thread);
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }

        Integer maxCount = 0;
        Integer freq = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                freq = entry.getKey();
            }
        }
        System.out.println("Самое частое количество повторений " + freq + " (встретилось " + maxCount + " раз)");
        System.out.println("Другие размеры:");

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}