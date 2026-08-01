class Solution {
    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        // this is dp[i][j] stores max score difference current player can get from nums[i..j]
        int[][] dp = new int[n][n];

        // this is base case: single element subarray, current player takes it
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }

        // this is filling dp table for increasing subarray lengths
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                // this is current player picks either nums[i] or nums[j]
                // then opponent plays optimally on remaining subarray
                // dp[i+1][j] and dp[i][j-1] represent opponent's best advantage
                // so current player's net = choice - opponent's advantage
                dp[i][j] = Math.max(
                    nums[i] - dp[i + 1][j],   // this is picking left end
                    nums[j] - dp[i][j - 1]    // this is picking right end
                );
            }
        }

        // this is player 1 wins if score difference is >= 0
        return dp[0][n - 1] >= 0;
    }
}
