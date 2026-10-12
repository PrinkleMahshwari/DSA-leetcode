import java.util.*;

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int[] bits = new int[31];
        long mod = 1_000_000_007L;

        // Count how many numbers have each bit set
        for (int num : nums) {
            for (int b = 0; b < 31; b++) {
                if ((num & (1 << b)) != 0) {
                    bits[b]++;
                }
            }
        }

        long result = 0;

        // Greedily build the largest k numbers
        for (int i = 0; i < k; i++) {
            long num = 0;

            for (int b = 0; b < 31; b++) {
                if (bits[b] > 0) {
                    num |= (1L << b);
                    bits[b]--;
                }
            }

            result = (result + num * num) % mod;
        }

        return (int) result;
    }
}