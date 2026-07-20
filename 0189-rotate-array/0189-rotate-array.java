class Solution {

    static {
        System.gc(); // encourages garbage collection early if needed
    }
    
    public void rotate(int[] nums, int k) {
        
        int n = nums.length;

        // rotating n, 2n, 3n... times produces the same array
        k %= n;

        // no rotation required
        if (k == 0) return;

        // reverse the entire array
        reverse(nums, 0, n - 1);

        // reverse first k elements
        reverse(nums, 0, k - 1);

        // reverse remaining elements
        reverse(nums, k , n - 1);
    }

    private void reverse(int[] nums, int start, int end) {

        while (start < end) {

            // swap both elements
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            // move both elements
            start++;
            end--;
        }
    }
}