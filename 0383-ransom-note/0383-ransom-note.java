class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        int rLen = ransomNote.length();
        int mLen = magazine.length();
        
        // Fast boundary optimization
        if (rLen > mLen) {
            return false;
        }
        
        // Primitive frequency array bypassing all object allocations
        int[] counts = new int[26];
        
        // Step 1: Record letter inventory from the magazine
        for (int i = 0; i < mLen; i++) {
            counts[magazine.charAt(i) - 'a']++;
        }
        
        // Step 2: Validate and consume for the ransom note
        for (int i = 0; i < rLen; i++) {
            int index = ransomNote.charAt(i) - 'a';
            counts[index]--;
            
            // If frequency drops below 0, the magazine ran out of this letter
            if (counts[index] < 0) {
                return false;
            }
        }
        
        return true;
    }
}
