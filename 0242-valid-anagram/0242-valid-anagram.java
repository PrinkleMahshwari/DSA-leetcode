class Solution {
    public boolean isAnagram(String s, String t) {
        // Step 1: Guard check for length mismatch
        if (s.length() != t.length()) {
            return false;
        }
        
        // Primitive table to balance frequencies
        int[] charCounts = new int[26];
        int len = s.length();
        
        // Step 2: Process increments and decrements in a single pass
        for (int i = 0; i < len; i++) {
            charCounts[s.charAt(i) - 'a']++;
            charCounts[t.charAt(i) - 'a']--;
        }
        
        // Step 3: Ensure all tracking balances returned perfectly back to 0
        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }
        
        return true;
    }
}
