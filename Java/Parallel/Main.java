import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    public static char[] readTextFromFile() {
        /*
            Legge dal testo e toglie caratteri speciali ( ();:,. ). Inoltre imposta tutto in Lowercase
        */
        Path path = Paths.get("/Users/marco/Project/ParallelComputing-TextBigrams-Trigrams/Java/Parallel/out/production/Parallel/text50KB.txt");

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


    public static ConcurrentHashMap<String, Integer> HashMerge(ConcurrentHashMap<String, Integer> n_grams, ConcurrentHashMap<String, Integer> finalNgrams) {
        /*
            Fa il merge delle mappe generate da ciascun thread
         */
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

    // MAIN
    public static void main(String[] args) {
        char[] text = readTextFromFile();  // leggo il testo
        int n_g = 3;   // set n_grams
        int n_thread = 8;  // set thread number

        int fileLen = text.length;  // salvo la lunghezza del testo

        ConcurrentHashMap<String, Integer> finalNgrams = new ConcurrentHashMap(); // Creo una HashMap contente <gram, value>
        ArrayList<Future> futuresArray = new ArrayList<>();  // Creo una ArrayList di Future nel quale ci inserirò i thread
        ExecutorService executor = Executors.newFixedThreadPool(n_thread);

        long start, end;
        start = System.currentTimeMillis();

        double k = Math.floor(fileLen/n_thread); // divido il testo in base al numero di thread
        for (int i = 0; i < n_thread; i++) {
            Future f = executor.submit(new Parallel_thread("t" + i, i * k, ((i+1) * k) + (n_g - 1) - 1, n_g, text));
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

            // Per la stampa
            Set set = finalNgrams.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) {
                Map.Entry map_entry = (Map.Entry)iterator.next();
                System.out.print("key: "+ map_entry.getKey() + " , value: ");
                System.out.println(map_entry.getValue());
            }

            System.out.println(end - start);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
