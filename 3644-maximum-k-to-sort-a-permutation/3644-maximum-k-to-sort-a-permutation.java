class Solution {
    public int sortPermutation(int[] nums) {
        int n = nums.length;
        int k = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                if (k == -1) k = i & nums[i];
                else k &= i & nums[i];
            }
        }

        return k == -1 ? 0 : k;
    }
}