import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return result;
        }

        int stringLen = s.length();
        int numWords = words.length;
        int wordLen = words[0].length();
        int totalWordsLen = numWords * wordLen;

        // Step 1: Precompute word target counts
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        // Step 2: Run separate sliding windows across all possible word offsets
        for (int i = 0; i < wordLen; i++) {
            int left = i;
            int right = i;
            int count = 0;
            Map<String, Integer> currentWindow = new HashMap<>();

            // Slide the window forward by hopping wordLen steps
            while (right + wordLen <= stringLen) {
                // Squeeze out the next trailing word token block
                String word = s.substring(right, right + wordLen);
                right += wordLen;

                if (wordCount.containsKey(word)) {
                    currentWindow.put(word, currentWindow.getOrDefault(word, 0) + 1);
                    count++;

                    // If we have too many occurrences of this word, shrink the window from the left
                    while (currentWindow.get(word) > wordCount.get(word)) {
                        String leftWord = s.substring(left, left + wordLen);
                        currentWindow.put(leftWord, currentWindow.get(leftWord) - 1);
                        count--;
                        left += wordLen;
                    }

                    // If the count of matching blocks equals target tokens, we found a match
                    if (count == numWords) {
                        result.add(left);
                    }
                } else {
                    // Invalid word encountered: clear the window completely and reset pointers
                    currentWindow.clear();
                    count = 0;
                    left = right;
                }
            }
        }

        return result;
    }
}
