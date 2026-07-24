class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        
        int prev2 = 0; // Represents DP[i-2]
        int prev1 = 0; // Represents DP[i-1]
        
        for (int num : nums) {
            int currentMax = Math.max(prev1, prev2 + num);
            
            // Advance pointers for the next iteration step
            prev2 = prev1;
            prev1 = currentMax;
        }
        
        return prev1;
    }
}
