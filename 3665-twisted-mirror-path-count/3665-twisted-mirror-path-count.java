class Solution {
    private static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // go[i][j][0] = destination when moving right
        // go[i][j][1] = destination when moving down
        int[][][] go = new int[m][n][2];

        // precompute transitions in reverse row-major order
        for (int i = m -1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                // moving right
                if (j + 1 < n) {
                    if (grid[i][j + 1] == 0) go[i][j][0] = i * n + (j + 1);
                    else go[i][j][0] = go[i][j + 1][1];
                } else {
                    go[i][j][0] = -1;
                }

                // moving down
                if (i + 1 < m) {
                    if (grid[i + 1][j] == 0) go[i][j][1] = (i + 1) * n + j;
                    else go[i][j][1] = go[i + 1][j][0];
                } else {
                    go[i][j][1] = -1;
                }
            }
        }    

        // dynamic programming
        int[][] dp = new int[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == 0) continue;

                // try both possible movements
                for (int d = 0; d < 2; d++) {
                    int next = go[i][j][d];

                    if (next == -1) continue;
                    int x = next / n;
                    int y = next % n;

                    dp[x][y] = (int) ((dp[x][y] + (long) dp[i][j]) % MOD);
                }
            }
        }

        return dp[m - 1][n - 1];
    }
}