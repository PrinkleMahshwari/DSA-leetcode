import java.util.*;

class Solution {

    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {

        int n = nums.length;

        // size dp by the actual max value present, not a fixed 200 --
        // gcd of any subset can never exceed the largest number involved,
        // so smaller inputs get a smaller table automatically
        int maxVal = 1;
        for (int num : nums) maxVal = Math.max(maxVal, num);

        int size = maxVal + 1;

        // precompute gcd table once, flattened to 1D for cache locality
        // (avoids the double pointer-chase of int[][] on every access)
        int[] gcdTable = new int[size * size];
        for (int a = 0; a <= maxVal; a++) {
            for (int b = 0; b <= maxVal; b++) {
                gcdTable[a * size + b] = gcd(a, b);
            }
        }

        // flattened rolling dp: dp[g1 * size + g2]. 1D arrays avoid the
        // row-object indirection of int[][], which was likely the main
        // cost given how tight the actual operation count is (~8M)
        long[] dp = new long[size * size];
        long[] next = new long[size * size];
        dp[0] = 1; // g1=0, g2=0 both empty, 1 way before processing anything

        for (int num : nums) {
            Arrays.fill(next, 0L); // single flat fill instead of per-row fill loop

            int numRow = num * size;

            for (int g1 = 0; g1 <= maxVal; g1++) {
                int g1Base = g1 * size;
                int gcdG1Row = g1 * size; // reused for gcdTable[g1][num] lookups

                for (int g2 = 0; g2 <= maxVal; g2++) {
                    long ways = dp[g1Base + g2];
                    if (ways == 0) continue;

                    int idx = g1Base + g2;

                    // choice 1: skip this element
                    next[idx] = (next[idx] + ways) % MOD;

                    // choice 2: add to seq1
                    int newG1 = (g1 == 0) ? num : gcdTable[gcdG1Row + num];
                    int idx2 = newG1 * size + g2;
                    next[idx2] = (next[idx2] + ways) % MOD;

                    // choice 3: add to seq2
                    int newG2 = (g2 == 0) ? num : gcdTable[g2 * size + num];
                    int idx3 = g1Base + newG2;
                    next[idx3] = (next[idx3] + ways) % MOD;
                }
            }

            // swap references, no reallocation per element
            long[] tmp = dp;
            dp = next;
            next = tmp;
        }

        long answer = 0;
        for (int g = 1; g <= maxVal; g++) {
            answer = (answer + dp[g * size + g]) % MOD;
        }

        return (int) answer;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}