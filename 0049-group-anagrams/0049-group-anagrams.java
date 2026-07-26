import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> anagramGroups = new HashMap<>();
        
        for (String str : strs) {
            // Allocate a fast primitive frequency counter for lowercase letters
            int[] counts = new int[26];
            int len = str.length();
            for (int i = 0; i < len; i++) {
                counts[str.charAt(i) - 'a']++;
            }
            
            // Build a unique, deterministic string signature key
            StringBuilder sb = new StringBuilder();
            for (int count : counts) {
                sb.append('#').append(count);
            }
            String key = sb.toString();
            
            // Group the original word string under its character layout footprint
            if (!anagramGroups.containsKey(key)) {
                anagramGroups.put(key, new ArrayList<>());
            }
            anagramGroups.get(key).add(str);
        }
        
        return new ArrayList<>(anagramGroups.values());
    }
}
