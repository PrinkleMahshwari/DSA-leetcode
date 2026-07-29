class Solution {
    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int[] totalCounts = new int[26];
        for (int i = 0; i < n; i++) {
            totalCounts[s.charAt(i) - 'a']++;
        }
        
        int halfLen = n / 2;
        int[] halfCounts = new int[26];
        char midChar = 0;
        
        for (int i = 0; i < 26; i++) {
            halfCounts[i] = totalCounts[i] / 2;
            if (totalCounts[i] % 2 != 0) {
                midChar = (char) (i + 'a');
            }
        }
        
        char[] firstHalf = new char[halfLen];
        int remainingSlots = halfLen;
        
        // Build the first half position by position
        for (int pos = 0; pos < halfLen; pos++) {
            boolean found = false;
            for (int c = 0; c < 26; c++) {
                if (halfCounts[c] > 0) {
                    halfCounts[c]--;
                    remainingSlots--;
                    
                    // Calculate remaining permutations capping at k + 1 to avoid overflow
                    long p = getPermutations(halfCounts, remainingSlots, k);
                    
                    if (p >= k) {
                        firstHalf[pos] = (char) (c + 'a');
                        found = true;
                        break; // Character locked into place
                    } else {
                        k -= p;
                        halfCounts[c]++;
                        remainingSlots++;
                    }
                }
            }
            if (!found) return ""; // Fewer than k permutations available
        }
        
        // If k is still greater than 0 after checking all combinations, return empty string
        if (k > 1) return "";
        
        // Construct the full mirrored palindrome string
        StringBuilder sb = new StringBuilder();
        String halfStr = new String(firstHalf);
        sb.append(halfStr);
        if (n % 2 != 0) {
            sb.append(midChar);
        }
        sb.append(new StringBuilder(halfStr).reverse().toString());
        
        return sb.toString();
    }
    
    // Helper to calculate multinomial coefficient capped at threshold
    private long getPermutations(int[] counts, int total, int threshold) {
        long res = 1;
        int currentTotal = 1;
        
        for (int i = 0; i < 26; i++) {
            int cnt = counts[i];
            for (int j = 1; j <= cnt; j++) {
                res = res * currentTotal / j;
                currentTotal++;
                if (res > threshold) {
                    return threshold + 1; // Cap to avoid integer overflow
                }
            }
        }
        return res;
    }
}
