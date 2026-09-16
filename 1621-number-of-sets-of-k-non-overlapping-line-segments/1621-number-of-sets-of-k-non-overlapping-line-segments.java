class Solution {
    public int numberOfSets(int n, int k) {
        final int MOD = 1_000_000_007;

        long[] dp = new long[n];

        // 0 segments -> 1 way for every prefix
        for (int i = 0; i < n; i++) dp[i] = 1;

        for (int j = 1; j <= k; j++) {
            long[] next = new long[n];
            long sum = 0;

            for (int i = 1; i < n; i++) {
                sum = (sum + dp[i - 1] % MOD);
                next[i] = (next[i - 1] + sum) % MOD;
            }
            dp = next;
        }
        return (int) dp[n - 1];
    }
}