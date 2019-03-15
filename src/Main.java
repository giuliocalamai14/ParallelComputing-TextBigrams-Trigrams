import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
// Per while
import java.util.Set;
import java.util.Iterator;
import java.util.Map.Entry;

public class Main {
    // Legge dal testo e toglie caratteri speciali ( ();:,. ). Inoltre imposta tutto in Lowercase
    public static char[] readTextFromFile() {
        Path path = Paths.get("/Users/marco/IdeaProjects/TextBigrams&Trigrams/src/text copy.txt");

        try {
            Stream<String> lines = Files.lines(path);
            char[] filestring = (lines.collect(Collectors.joining())).replaceAll("[ \\[\\]'();:,.]", "").toCharArray();

            for(int i = 0; i < filestring.length - 1; ++i) {
                if (Character.isUpperCase(filestring[i])) {
                    filestring[i] = Character.toLowerCase(filestring[i]);
                }
            }

            System.out.println(filestring);
            return filestring;

        }

        catch (IOException e) {
            System.out.println(e);
            System.exit(1);
            return null;
        }
    }


    public static void main(String[] args) {

        char[] text = readTextFromFile();
        long start, end;

        start = System.currentTimeMillis();
        HashMap hmap = Sequential.computeNGrams(3, text);
        end = System.currentTimeMillis();

         Set set = hmap.entrySet();
            Iterator iterator = set.iterator();

            while (iterator.hasNext()) {
                Entry map_entry = (Entry) iterator.next();
                System.out.print("key: " + map_entry.getKey() + " , value: ");
                System.out.println(map_entry.getValue());
            }

        System.out.println(end - start);

    }
}
