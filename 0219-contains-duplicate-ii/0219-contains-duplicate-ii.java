import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // Map to store: Key = Number, Value = Its most recent index position
        Map<Integer, Integer> lastSeenIndex = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int currentNum = nums[i];
            
            // Check if we have encountered this number before
            if (lastSeenIndex.containsKey(currentNum)) {
                int prevIndex = lastSeenIndex.get(currentNum);
                
                // Validate if the distance between indices meets the constraint
                if (i - prevIndex <= k) {
                    return true;
                }
            }
            
            // Update the map with the latest index for this number
            lastSeenIndex.put(currentNum, i);
        }
        
        return false;
    }
}
