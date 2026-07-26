import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        int len = pattern.length();
        
        // Fast boundary check
        if (len != words.length) {
            return false;
        }

        // Fast array buffer for the 26 pattern characters
        int[] charLastSeen = new int[26];
        
        // Map to track the last seen index of words
        Map<String, Integer> wordLastSeen = new HashMap<>();

        for (int i = 0; i < len; i++) {
            char ch = pattern.charAt(i);
            String word = words[i];
            
            int charIndex = ch - 'a';
            
            // Fetch the last seen 1-based index for both (0 means never seen)
            int lastSeenCharPos = charLastSeen[charIndex];
            int lastSeenWordPos = wordLastSeen.getOrDefault(word, 0);

            // If their historical tracking positions mismatch, the bijection is broken
            if (lastSeenCharPos != lastSeenWordPos) {
                return false;
            }

            // Update the positions with the current 1-based index
            charLastSeen[charIndex] = i + 1;
            wordLastSeen.put(word, i + 1);
        }

        return true;
    }
}
