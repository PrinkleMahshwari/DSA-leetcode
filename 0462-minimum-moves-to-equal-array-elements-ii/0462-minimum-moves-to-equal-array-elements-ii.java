class Solution {
    public int minMoves2(int[] nums) {
        // sort the array
        Arrays.sort (nums);

        // find the median
        int median = nums[nums.length / 2];

        long moves = 0;

        // calculate the total distance from the median
        for (int num : nums) moves += Math.abs ((long) num - median);

        return (int) moves;
    }
}