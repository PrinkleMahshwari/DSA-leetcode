import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        // Fast optimization check: if note is longer than the supply, it's impossible
        if (ransomNote.length() > magazine.length()) {
            return false;
        }

        Map<Character, Integer> letterCounts = new HashMap<>();

        // Step 1: Record all available characters from the magazine
        for (int i = 0; i < magazine.length(); i++) {
            char ch = magazine.charAt(i);
            letterCounts.put(ch, letterCounts.getOrDefault(ch, 0) + 1);
        }

        // Step 2: Consume characters to build the ransom note
        for (int i = 0; i < ransomNote.length(); i++) {
            char ch = ransomNote.charAt(i);
            int availableCount = letterCounts.getOrDefault(ch, 0);

            // If the letter is missing or completely used up, we fail
            if (availableCount == 0) {
                return false;
            }

            // Decrement the letter pool count
            letterCounts.put(ch, availableCount - 1);
        }

        return true;
    }
}
