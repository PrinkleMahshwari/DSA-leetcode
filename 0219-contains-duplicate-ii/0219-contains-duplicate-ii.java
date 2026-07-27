import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // Fast boundary optimization
        if (nums == null || nums.length <= 1 || k <= 0) {
            return false;
        }

        // Set size is naturally bounded to a maximum of k elements
        Set<Integer> window = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            // Step 1: If the number already exists in our active window of size k, we found a match!
            if (!window.add(nums[i])) {
                return true;
            }

            // Step 2: Once the window exceeds size k, remove the oldest element from the left
            if (i >= k) {
                window.remove(nums[i - k]);
            }
        }

        return false;
    }
}
