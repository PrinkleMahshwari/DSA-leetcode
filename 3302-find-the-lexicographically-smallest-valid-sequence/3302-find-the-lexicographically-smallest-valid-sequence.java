class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();

        // suffixLen[i] represents the maximum length of a suffix of word2 
        // that can be formed using characters from word1 starting from index i.
        int[] suffixLen = new int[n + 1];
        
        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            suffixLen[i] = suffixLen[i + 1];
            if (j >= 0 && w1[i] == w2[j]) {
                suffixLen[i]++;
                j--;
            }
        }

        int[] answer = new int[m];
        int w2Idx = 0; // tracking pointer inside word2
        boolean changed = false; // flag indicating if the single modification was consumed

        for (int i = 0; i < n && w2Idx < m; i++) {
            // Case 1: The current characters match perfectly
            if (w1[i] == w2[w2Idx]) {
                answer[w2Idx] = i;
                w2Idx++;
            } 
            // Case 2: Mismatch, but we haven't changed a character yet.
            // Check if the rest of word2 (which has a length of m - w2Idx - 1) 
            // can be completely formed from word1 starting strictly from index i + 1.
            else if (!changed && suffixLen[i + 1] >= (m - w2Idx - 1)) {
                answer[w2Idx] = i;
                w2Idx++;
                changed = true; // consume the mutation slot
            }
        }

        // If the pointer did not fully process word2, no valid configuration exists
        if (w2Idx < m) {
            return new int[0];
        }

        return answer;
    }
}
