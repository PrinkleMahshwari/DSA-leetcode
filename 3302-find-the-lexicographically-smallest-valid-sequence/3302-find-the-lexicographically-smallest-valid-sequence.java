class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();

        // Memory Optimized: Size m array instead of size n.
        // lastValidW1Idx[j] stores the LARGEST index in word1 
        // that can successfully match the character word2[j] 
        // while safely completing the valid suffix from j to m-1.
        int[] lastValidW1Idx = new int[m];
        
        // Initialize with an invalid flag state
        for (int i = 0; i < m; i++) {
            lastValidW1Idx[i] = -1;
        }

        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && w1[i] == w2[j]) {
                lastValidW1Idx[j] = i;
                j--;
            }
        }

        int[] answer = new int[m];
        int w2Idx = 0; 
        boolean changed = false; 

        for (int i = 0; i < n && w2Idx < m; i++) {
            // Case 1: Fast direct character match
            if (w1[i] == w2[w2Idx]) {
                answer[w2Idx] = i;
                w2Idx++;
            } 
            // Case 2: Mismatch mutation option.
            // If it's the last character of word2, it's always safe to change.
            // Otherwise, verify if the next suffix (w2Idx + 1) can start strictly after index i.
            else if (!changed && (w2Idx == m - 1 || (lastValidW1Idx[w2Idx + 1] != -1 && lastValidW1Idx[w2Idx + 1] > i))) {
                answer[w2Idx] = i;
                w2Idx++;
                changed = true; 
            }
        }

        // If the pointer did not fully finish word2, no valid sequence exists
        if (w2Idx < m) {
            return new int[0];
        }

        return answer;
    }
}
