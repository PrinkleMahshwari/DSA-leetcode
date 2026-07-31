import java.util.Arrays;

class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];
        int len = word.length();
        
        // FIX 1: Direct character scanning via charAt() to bypass heap allocation
        for (int i = 0; i < len; i++) {
            freq[word.charAt(i) - 'a']++;
        }

        // FIX 2: Fast primitive sort on the fixed 26-slot stack boundaries
        Arrays.sort(freq);

        int totalPushes = 0;
        int keyIndex = 0;

        // Step 3: Scan backwards (highest frequencies first) to assign push costs
        for (int i = 25; i >= 0; i--) {
            if (freq[i] == 0) {
                break; // Stop immediately once all existing characters are processed
            }
            
            // Push cost: first 8 get 1 push, next 8 get 2 pushes, etc.
            int pushCost = (keyIndex / 8) + 1;
            totalPushes += freq[i] * pushCost;
            keyIndex++;
        }

        return totalPushes;
    }
}
