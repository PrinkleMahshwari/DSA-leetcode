class Solution {

    private int[][] dp;
    private int[] suffix;
    private int n;

    public int stoneGameII(int[] piles) {
        
        n = piles.length;

        dp = new int[n][n + 1];
        suffix = new int[n + 1];

        // suffix[i] = sum of piles[i ... n - 1];
        for (int i = n - 1; i >= 0; i--)
            suffix[i] = suffix[i + 1] + piles[i];
        
        return solve(0, 1);
    }

    private int solve(int i, int m) {

        // No piles left
        if (i >= n)
            return 0;
        
        // If we can take all remaining piles
        if (2 * m >= n - i)
            return suffix[i];
        
        if (dp[i][m] != 0)
            return dp[i][m];
        
        int best = 0;

        for (int x = 1; x <= 2 * m; x++) {

            int nextM = Math.max(m,x);

            int opponent = solve(i + x, nextM);

            int current = suffix[i] - opponent;

            best = Math.max(best, current);
        }

        return dp[i][m] = best;
    }
}