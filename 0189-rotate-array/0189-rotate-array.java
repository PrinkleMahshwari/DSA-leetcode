class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        if (k == 0) return;

        // Step 1: Inline Reverse Entire Array
        int start = 0, end = n - 1;
        while (start < end) {
            int temp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = temp;
        }

        // Step 2: Inline Reverse First K Elements
        start = 0; 
        end = k - 1;
        while (start < end) {
            int temp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = temp;
        }

        // Step 3: Inline Reverse Remaining Elements
        start = k; 
        end = n - 1;
        while (start < end) {
            int temp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = temp;
        }
    }
}
