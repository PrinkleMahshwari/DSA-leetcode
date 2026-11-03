class Solution {
    public int minMoves(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum = 0;

        // find max and calculate the sum
        for (int num : nums) {
            max = Math.max (max, num);
            sum += num;
        }

        // calculate the min moves
        return nums.length * max - sum;
    }
}