import java.util.*;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        // Convert to a HashSet for O(1) lookups
        Set<String> wordSet = new HashSet<>(wordDict);
        
        // Find the length of the longest word in the dictionary to prune loops
        int maxWordLen = 0;
        for (String word : wordDict) {
            maxWordLen = Math.max(maxWordLen, word.length());
        }
        
        // dp[i] means s.substring(0, i) can be segmented
        boolean[] dp = new boolean[n + 1];
        dp[0] = true; // Base case: empty string
        
        for (int i = 1; i <= n; i++) {
            // Scan backwards to find a valid partition point j
            for (int j = i - 1; j >= 0; j--) {
                // Optimization: word length cannot exceed our max dictionary word size
                if (i - j > maxWordLen) {
                    break; 
                }
                
                // If the prefix up to j is valid, check if the remaining substring exists
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break; // Found a valid segmentation for position i, move on
                }
            }
        }
        
        return dp[n];
    }
}
