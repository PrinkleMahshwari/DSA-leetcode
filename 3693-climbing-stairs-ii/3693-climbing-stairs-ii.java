class Solution {
    public int climbStairs(int n, int[] costs) {
        long INF = Long.MAX_VALUE / 4;
        long prev1 = 0; // dp[i - 1]
        long prev2 = INF; // dp[i - 2]
        long prev3 = INF; // dp[i - 3]

        for (int i = 1; i <= n; i++) {
            long current = Math.min (prev1 + 1, Math.min (prev2 + 4, prev3 + 9));
            current += costs[i - 1];

            // shift previous dp states
            prev3 = prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return (int) prev1;
    }
}