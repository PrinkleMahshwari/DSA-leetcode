class Solution {
    public int majorityElement(int[] nums) {

        int candidate = nums[0];
        int count = 1; // start at 1 since candidate is nums[0] itself, skip index 0 in the loop

        int n = nums.length;
        for (int i = 1; i < n; i++) {
            int num = nums[i];

            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;
    }
}