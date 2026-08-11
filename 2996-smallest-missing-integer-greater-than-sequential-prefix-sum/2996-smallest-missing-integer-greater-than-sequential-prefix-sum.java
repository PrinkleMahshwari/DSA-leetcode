import java.util.HashSet;
import java.util.Set;

class Solution {
    public int missingInteger(int[] nums) {
        int prefixSum = nums[0];

        // 1. calculate the sum of the longest sequential prefix
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1)
                prefixSum += nums[i];
            else
                break; // break early if the sequence terminates
        }

        // 2. add all elements to a HashSet for O(1) member lookup
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums)
            numSet.add(num);
        
        // 3. increment prefixsum until we find a value missing from set
        while (numSet.contains(prefixSum)) 
            prefixSum++;
        
        return prefixSum;
    }
}