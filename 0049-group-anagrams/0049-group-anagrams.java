import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        // Use a map with a tight initial capacity layout to avoid internal rehashing buckets
        Map<String, List<String>> anagramGroups = new HashMap<>(strs.length);
        
        for (String str : strs) {
            // Convert to primitive char array for native memory sorting operations
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            
            // Re-wrap the sorted primitive array directly as our tracking key string
            String key = new String(chars);
            
            // Retrieve or create the grouping array list using Java 8 computeIfAbsent
            anagramGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(anagramGroups.values());
    }
}
