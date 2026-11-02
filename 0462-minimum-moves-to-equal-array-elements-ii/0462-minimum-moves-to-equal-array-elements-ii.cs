using System;

public class Solution {
    public int MinMoves2(int[] nums) {
        Array.Sort(nums);

        int median = nums[nums.Length / 2];
        long moves = 0; // Using long to prevent integer overflow during summation

        foreach (int num in nums) {
            moves += Math.Abs((long)num - median);
        }

        return (int)moves;
    }
}
