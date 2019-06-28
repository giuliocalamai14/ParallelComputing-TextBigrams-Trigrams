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
    // Read from the text and remove special characters ( ();:,. ). Also set all in Lowercase
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
    // Marge n_grams in finalNgrams

        //long s, e;
        //s = System.currentTimeMillis();
        for (ConcurrentHashMap.Entry<String, Integer> entry : n_grams.entrySet()) {
            int newValue = entry.getValue();
            Integer existingValue = finalNgrams.get(entry.getKey());
            if (existingValue != null) {
                newValue = newValue + existingValue;
            }
            finalNgrams.put(entry.getKey(), newValue);
        }

        // e = System.currentTimeMillis();
        // System.out.println(e - s);
        // Tested that all HashMerge executes in 10-12 ms (average) in a total of 10 sec for the 150Mb txt

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
        char[] text = readTextFromFile();  // read text
        int n_g = 3;   // set n_grams
        int n_thread = 8;  // set threads number

        int fileLen = text.length;  // save text length

        ConcurrentHashMap<String, Integer> finalNgrams = new ConcurrentHashMap(); // Create ConcurrentHashMap cointaining <gram, value>
        ArrayList<Future> futuresArray = new ArrayList<>();  // Create an ArrayList of Future that will contain the threads results
        ExecutorService executor = Executors.newFixedThreadPool(n_thread); // create the threads

        long start, end;
        start = System.currentTimeMillis();

        double k = Math.floor(fileLen/n_thread); // divide text according to number of threads
        for (int i = 0; i < n_thread; i++) {
            Future f = executor.submit(new Parallel_thread("t" + i, i * k, ((i+1) * k) + (n_g - 1) - 1, n_g, text)); //executor.submit start the Callable call that save the results in a Future
            futuresArray.add(f);
        }
        try{
            for (Future <ConcurrentHashMap<String, Integer>> f : futuresArray) {
                ConcurrentHashMap<String, Integer> n_grams = f.get();  // f.get return the thread result
                HashMerge(n_grams,finalNgrams);
            }
            awaitTerminationAfterShutdown(executor);
            //System.out.println("Finished all threads");
            end = System.currentTimeMillis();

            // Print Map
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
