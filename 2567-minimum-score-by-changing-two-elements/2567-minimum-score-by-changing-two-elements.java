class Solution {
    public int minimizeSum(int[] nums) {
        Arrays.sort (nums);

        int n = nums.length, case1 = nums[n - 1] - nums[2], case2 = nums[n - 2] - nums[1], case3 = nums[n - 3] - nums[0];

        return Math.min (case1, Math.min (case2, case3));
    }
}