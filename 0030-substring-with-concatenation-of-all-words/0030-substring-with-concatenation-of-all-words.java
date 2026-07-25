import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0) return result;

        int n = s.length();
        int numWords = words.length;
        int wordLen = words[0].length();
        int totalLen = numWords * wordLen;

        if (n < totalLen) return result;

        // Step 1: Map each unique word string to a small unique primitive integer ID
        Map<String, Integer> wordToId = new HashMap<>();
        int uniqueIdCounter = 0;

        for (String word : words) {
            if (!wordToId.containsKey(word)) {
                wordToId.put(word, uniqueIdCounter++);
            }
        }

        // Frequency of each word ID in the target dictionary
        int[] targetFreq = new int[uniqueIdCounter];
        for (String word : words) {
            targetFreq[wordToId.get(word)]++;
        }

        // Step 2: Pre-convert string s into an integer ID array
        int[] sIds = new int[n - wordLen + 1];
        Arrays.fill(sIds, -1);

        for (int i = 0; i <= n - wordLen; i++) {
            String sub = s.substring(i, i + wordLen);
            Integer id = wordToId.get(sub);
            if (id != null) {
                sIds[i] = id;
            }
        }

        // Reusable frequency array for the current sliding window
        int[] currentFreq = new int[uniqueIdCounter];

        // Step 3: Sliding window across each possible offset
        for (int i = 0; i < wordLen; i++) {
            int left = i;
            int right = i;
            int count = 0;

            Arrays.fill(currentFreq, 0);

            while (right + wordLen <= n) {
                int id = sIds[right];
                right += wordLen;

                if (id != -1) {
                    currentFreq[id]++;
                    count++;

                    // Too many occurrences of this word -> shrink window
                    while (currentFreq[id] > targetFreq[id]) {
                        int leftId = sIds[left];
                        currentFreq[leftId]--;
                        count--;
                        left += wordLen;
                    }

                    // Found a valid concatenation
                    if (count == numWords) {
                        result.add(left);

                        // Immediately slide the window forward by one word
                        int leftId = sIds[left];
                        currentFreq[leftId]--;
                        count--;
                        left += wordLen;
                    }
                } else {
                    // Invalid word encountered -> reset window
                    if (count > 0) {
                        Arrays.fill(currentFreq, 0);
                        count = 0;
                    }
                    left = right;
                }
            }
        }

        return result;
    }
}