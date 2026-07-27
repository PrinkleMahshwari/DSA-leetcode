class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // ASCII frequency tracking arrays to skip HashMap instantiation overhead
        int[] targetCounts = new int[128];
        int[] windowCounts = new int[128];

        // Step 1: Populate target frequencies from string t
        int requiredMatches = 0;
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            if (targetCounts[ch] == 0) {
                requiredMatches++; // Count unique characters to satisfy
            }
            targetCounts[ch]++;
        }

        int left = 0;
        int right = 0;
        int formedMatches = 0;

        // Variables tracking our optimal result window
        int minLen = Integer.MAX_VALUE;
        int minLeftStart = 0;

        // Step 2: Expand the right side of the window
        while (right < s.length()) {
            char rightChar = s.charAt(right);
            windowCounts[rightChar]++;

            // If the character count matches its exact requirement in t, increment match metric
            if (targetCounts[rightChar] > 0 && windowCounts[rightChar] == targetCounts[rightChar]) {
                formedMatches++;
            }

            // Step 3: Contract the window from the left once all requirements are met
            while (formedMatches == requiredMatches) {
                int currentWindowLen = right - left + 1;
                
                // Track the absolute smallest valid window coordinates
                if (currentWindowLen < minLen) {
                    minLen = currentWindowLen;
                    minLeftStart = left;
                }

                char leftChar = s.charAt(left);
                windowCounts[leftChar]--;

                // If dropping this character breaks our match requirements, exit contraction loop
                if (targetCounts[leftChar] > 0 && windowCounts[leftChar] < targetCounts[leftChar]) {
                    formedMatches--;
                }
                
                left++; // Shrink window bounds forward
            }
            
            right++; // Expand window bounds forward
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeftStart, minLeftStart + minLen);
    }
}
