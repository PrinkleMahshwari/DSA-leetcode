class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // a valid parentheses string must have even length
        if ((m + n - 1) % 2 == 1) return false;

        // first and last characters must be '(' and ')'
        if (grid[0][0] != '(' || grid[m - 1][n - 1] != ')') return false;

        int maxBalance = (m + n - 1) / 2;

        boolean[][][] dp = new boolean[m][n][maxBalance + 1];

        dp[0][0][1] = true;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (r == 0 && c == 0) continue;

                int change = grid[r][c] == '(' ? 1 : -1;

                for (int balance = 0; balance <= maxBalance; balance++) {
                    int previousBalance = balance - change;

                    if (previousBalance < 0 || previousBalance > maxBalance) continue;

                    boolean possible = false;

                    if (r > 0) possible |= dp[r - 1][c][previousBalance];

                    if (c > 0) possible |= dp[r][c - 1][previousBalance];

                    dp[r][c][balance] = possible;
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}