class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        // this is dp[i][j] stores max score difference current player can get from piles[i..j]
        int[][] dp = new int[n][n];

        // this is base case: single pile, current player takes it
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }

        // this is filling dp table for increasing subarray lengths
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // this is current player picks either piles[i] or piles[j]
                // opponent plays optimally on remaining, so subtract opponent's advantage
                dp[i][j] = Math.max(
                    piles[i] - dp[i + 1][j],   // this is picking left end
                    piles[j] - dp[i][j - 1]    // this is picking right end
                );
            }
        }

        // this is Alice wins if score difference is > 0
        return dp[0][n - 1] > 0;
    }
}
