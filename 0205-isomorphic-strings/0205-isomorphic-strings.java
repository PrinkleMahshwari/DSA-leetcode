class Solution {
    public boolean isIsomorphic(String s, String t) {
        int len = s.length();
        
        // Fast array buffers tracking the last-seen 1-based index position of characters
        int[] mapS = new int[256];
        int[] mapT = new int[256];
        
        for (int i = 0; i < len; i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);
            
            // If their previous structural occurrences don't match, they aren't isomorphic
            if (mapS[charS] != mapT[charT]) {
                return false;
            }
            
            // Record current position using 1-based indexing (reserving 0 for default/unseen)
            mapS[charS] = i + 1;
            mapT[charT] = i + 1;
        }
        
        return true;
    }
}
