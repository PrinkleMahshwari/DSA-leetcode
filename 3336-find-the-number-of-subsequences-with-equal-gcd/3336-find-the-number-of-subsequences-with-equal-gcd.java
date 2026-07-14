class Solution {

    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {

        int n = nums.length;
        int maxVal = 200;

        // precompute gcd table for 0..maxVal, avoids recomputing gcd(a,b)
        // repeatedly inside the hot triple-nested loop below
        int[][] gcdTable = new int[maxVal + 1][maxVal + 1];
        for (int a = 0; a <= maxVal; a++) {
            for (int b = 0; b <= maxVal; b++) {
                gcdTable[a][b] = gcd(a, b);
            }
        }

        // dp[g1][g2] = number of ways to split processed elements into
        // (seq1 with gcd g1, seq2 with gcd g2, unused elements)
        // g=0 means that subsequence is still empty so far
        long[][] dp = new long[maxVal + 1][maxVal + 1];
        dp[0][0] = 1; // before processing anything, both sequences empty, 1 way

        // rolling dp: only current and next layer ever needed, no full 3D array
        long[][] next = new long[maxVal + 1][maxVal + 1];

        for (int num : nums) {
            // reset next layer for this element's transitions
            for (long[] row : next) Arrays.fill(row, 0);

            for (int g1 = 0; g1 <= maxVal; g1++) {
                for (int g2 = 0; g2 <= maxVal; g2++) {
                    long ways = dp[g1][g2];
                    if (ways == 0) continue;

                    // choice 1: skip this element entirely
                    next[g1][g2] = (next[g1][g2] + ways) % MOD;

                    // choice 2: add to seq1, gcd updates (0 acts as identity here)
                    int newG1 = (g1 == 0) ? num : gcdTable[g1][num];
                    next[newG1][g2] = (next[newG1][g2] + ways) % MOD;

                    // choice 3: add to seq2, gcd updates
                    int newG2 = (g2 == 0) ? num : gcdTable[g2][num];
                    next[g1][newG2] = (next[g1][newG2] + ways) % MOD;
                }
            }

            // swap current and next, avoids allocating a fresh array per element
            long[][] tmp = dp;
            dp = next;
            next = tmp;
        }

        // final answer: both sequences non-empty (g >= 1) and equal gcd
        long answer = 0;
        for (int g = 1; g <= maxVal; g++) {
            answer = (answer + dp[g][g]) % MOD;
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