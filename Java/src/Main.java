import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    // Legge dal testo e toglie caratteri speciali ( ();:,. ). Inoltre imposta tutto in Lowercase
    public static char[] readTextFromFile() {
        //Path path = Paths.get("/Users/giuliocalamai/IdeaProjects/ParallelComputing-TextBigrams-Trigrams/src/text.txt");
        Path path = Paths.get("/Users/marco/Desktop/ParallelComputing-TextBigrams-Trigrams/Java/src/text.txt");

        try {
            Stream<String> lines = Files.lines(path);
            char[] filestring = (lines.collect(Collectors.joining())).replaceAll("[ \" % $ £ & - + \\[\\]'();:,.]", "").toCharArray();

            for(int i = 0; i < filestring.length - 1; ++i) {
                if (Character.isUpperCase(filestring[i])) {
                    filestring[i] = Character.toLowerCase(filestring[i]);
                }
            }
            return filestring;
        }
        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
            return null;
        }
    }


    public static void startSequential(char[] text, int n_grams){
        long start, end;
        start = System.currentTimeMillis();
        HashMap hmap = Sequential.computeNGrams(n_grams, text);
        end = System.currentTimeMillis();

        /* Set set = hmap.entrySet();
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) {
                Entry map_entry = (Entry) iterator.next();
                System.out.print("key: " + map_entry.getKey() + " , value: ");
                System.out.println(map_entry.getValue());
            }*/

        System.out.println(end - start);

    }

    // PER PARALLEL
    public static ConcurrentHashMap<String, Integer> HashMerge(ConcurrentHashMap<String, Integer> n_grams, ConcurrentHashMap<String, Integer> finalNgrams) {
        for (ConcurrentHashMap.Entry<String, Integer> entry : n_grams.entrySet()) {
            int newValue = entry.getValue();
            Integer existingValue = finalNgrams.get(entry.getKey());
            if (existingValue != null) {
                newValue = newValue + existingValue;
            }
            finalNgrams.put(entry.getKey(), newValue);
        }
        return finalNgrams;
    }

    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void startParallel(char[] text, int n){
        int fileLen = text.length;
        //int threads = Runtime.getRuntime().availableProcessors();
        int realThreads = 2;

        ConcurrentHashMap<String, Integer> finalNgrams = new ConcurrentHashMap();
        ArrayList<Future> futuresArray = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(realThreads);

        long start, end;
        start = System.currentTimeMillis();


        double k = Math.floor(fileLen/realThreads);
        for (int i = 0; i < realThreads; i++) {
            Future f = executor.submit(new Parallel("t" + i, i * k, ((i+1) * k) + (n - 1) - 1, n, text));
            futuresArray.add(f);
        }
        try{
            for (Future <ConcurrentHashMap<String, Integer>> f : futuresArray) {
                ConcurrentHashMap<String, Integer> n_grams = f.get();
                HashMerge(n_grams,finalNgrams);
            }
            awaitTerminationAfterShutdown(executor);
            //System.out.println("Finished all threads");
            end = System.currentTimeMillis();

            /*Set set = finalNgrams.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Map.Entry map_entry = (Map.Entry)iterator.next();
                System.out.print("key: "+ map_entry.getKey() + " , value: ");
                System.out.println(map_entry.getValue());
            }*/
            System.out.println(end - start);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }



    public static void main(String[] args) {
        char[] text = readTextFromFile();
        int n_grams = 3;

        startSequential(text,n_grams);
        //startParallel(text,n_grams);
    }
}
