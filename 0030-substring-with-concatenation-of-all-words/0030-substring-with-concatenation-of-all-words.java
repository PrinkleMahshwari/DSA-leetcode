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
        int[] targetFreq = new int[words.length]; // Freq map indexed by Word ID
        int uniqueIdCounter = 0;

        for (String word : words) {
            Integer id = wordToId.get(word);
            if (id == null) {
                id = uniqueIdCounter++;
                wordToId.put(word, id);
            }
            targetFreq[id]++;
        }

        // Step 2: Pre-convert string s into an integer ID array to eliminate substring creation inside the loops
        int[] sIds = new int[n - wordLen + 1];
        Arrays.fill(sIds, -1); // -1 means no valid word from the dictionary starts here
        
        for (int i = 0; i <= n - wordLen; i++) {
            String sub = s.substring(i, i + wordLen);
            Integer id = wordToId.get(sub);
            if (id != null) {
                sIds[i] = id;
            }
        }

        // Reusable current window frequency tracking array to avoid object recreation
        int[] currentFreq = new int[uniqueIdCounter];

        // Step 3: Run the sliding window over the primitive int array across offsets
        for (int i = 0; i < wordLen; i++) {
            int left = i;
            int right = i;
            int count = 0;
            Arrays.fill(currentFreq, 0); // Reset tracking array for this offset branch

            while (right + wordLen <= n) {
                int id = sIds[right];
                right += wordLen;

                if (id != -1) {
                    currentFreq[id]++;
                    count++;

                    // If the word count exceeds the dictionary allocation rule, shrink left
                    while (currentFreq[id] > targetFreq[id]) {
                        int leftId = sIds[left];
                        currentFreq[leftId]--;
                        count--;
                        left += wordLen;
                    }

                    // Complete block sequence matched successfully
                    if (count == numWords) {
                        result.add(left);
                    }
                } else {
                    // Invalid chunk sequence hit: instantly wipe records and advance left window bound
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
