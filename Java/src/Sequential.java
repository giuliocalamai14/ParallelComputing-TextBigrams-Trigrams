import java.util.HashMap;

public class Sequential {
    public Sequential() {  //costruttore
    }

    public static HashMap<String, Integer> computeNGrams(int n, char[] fileString) {
        HashMap<String, Integer> hashMap = new HashMap();

        for(int i = 0; i < fileString.length - n + 1; ++i) {
            StringBuilder builder = new StringBuilder();

            for(int j = 0; j < n; ++j) {
                builder.append(fileString[i + j]);
            }

            String key = builder.toString();

            if (!hashMap.containsKey(key)) {
                hashMap.put(builder.toString(), 1);
            }
            else if (hashMap.containsKey(key)) {
                hashMap.put(builder.toString(), hashMap.get(key) + 1);
            }
        }

        //System.out.println(n + "-grams:");
        return hashMap;
    }

}
