class Solution {
    public boolean isSubsequence(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();
        
        // An empty string is always a subsequence of any string
        if (sLen == 0) return true;
        if (sLen > tLen) return false;
        
        int i = 0; // Pointer for s
        int j = 0; // Pointer for t
        
        while (j < tLen) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                // If we matched all characters of s, we are done
                if (i == sLen) {
                    return true;
                }
            }
            j++;
        }
        
        return false;
    }
}
