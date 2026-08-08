class Solution {
    public int[] validSequence(String word1, String word2) {
        
        int n = word1.length();
        int m = word2.length();

        int[] dp = new int[n + 1];
        // build dp from right to left
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1]; // dp[i + 1] tells us how many characters from the END of word2 already matched

            // word2[m - dp[i + 1] - 1] is the next required character
            if (dp[i + 1] < m && word1.charAt(i) == word2.charAt(m - dp[i + 1] - 1))
                dp[i]++;
            
        }

        // Greedily construct the answer from left to right
        int[] answer = new int[m];

        int j = 0; // current position in word2
        boolean changed = false; // have we already used our one mismatch?
        int size = 0; 

        for (int i = 0; i < n && j < m; i++) {
            // case 1: current character already matches word2[j] taking this index is always optimal beacause we want the lexicographically smallest index sequence
            if (word1.charAt(i) == word2.charAt(j)) {
                answer[size++] = i;
                j++;
            }

            // Case 2: characters don't match we may use this position as ONE allowed matc, but only if the remaining part of word2 can be matched from word[i + 1 ...]
            else if (!changed && dp[i + 1] >= m - j - 1) {
                answer[size++] = i;
                j++;
                changed = true;
            }
        }

        // if we don't select m indices, no valid sequence exists
        if (size != m) 
            return new int[0];
        
        return answer;
    }
}