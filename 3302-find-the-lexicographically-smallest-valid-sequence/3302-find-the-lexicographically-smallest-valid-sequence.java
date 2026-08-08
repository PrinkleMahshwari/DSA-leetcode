class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // Memory Fix: Avoid duplicating heavy strings as primitive arrays.
        // We use an int array of size m, which uses minimal memory space.
        int[] lastValidW1Idx = new int[m];
        
        // Inline fill to avoid array mutation overhead
        for (int i = 0; i < m; i++) {
            lastValidW1Idx[i] = -1;
        }

        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            // Using .charAt directly skips memory allocation overhead
            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                lastValidW1Idx[j] = i;
                j--;
            }
        }

        int[] answer = new int[m];
        int w2Idx = 0; 
        boolean changed = false; 

        for (int i = 0; i < n && w2Idx < m; i++) {
            char c1 = word1.charAt(i);
            char c2 = word2.charAt(w2Idx);

            // Case 1: Matching character configuration
            if (c1 == c2) {
                answer[w2Idx] = i;
                w2Idx++;
            } 
            // Case 2: Mutation substitution condition
            else if (!changed && (w2Idx == m - 1 || (lastValidW1Idx[w2Idx + 1] != -1 && lastValidW1Idx[w2Idx + 1] > i))) {
                answer[w2Idx] = i;
                w2Idx++;
                changed = true; 
            }
        }

        if (w2Idx < m) {
            return new int[0];
        }

        return answer;
    }
}
