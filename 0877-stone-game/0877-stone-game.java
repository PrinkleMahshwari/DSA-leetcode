class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        // this is 1D array since we only need previous diagonal
        int[] dp = new int[n];

        // this is base case: single piles
        for (int i = 0; i < n; i++) {
            dp[i] = piles[i];
        }

        // this is filling by length, updating dp in-place
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // this is picking best of left or right end
                dp[i] = Math.max(piles[i] - dp[i + 1], piles[j] - dp[i]);
            }
        }

        return dp[0] > 0;
    }
}
