class Solution {
    private int calculateCost (String s, int start, int len, int d) {
        int changes = 0, groupSize = len / d;

        for (int offset = 0; offset < d; offset++) {
            for (int left = 0; left < groupSize / 2; left++) {
                int right = groupSize - 1 - left;
                if (s.charAt (start + offset + left * d) != s.charAt (start + offset + right * d)) changes++;
            }
        }

        return changes;
    }

    public int minimumChanges(String s, int k) {
        int n = s.length (), INF = 1_000_000_000;

        // precompute the cost of every substring
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int len = 2; i + len <= n; len++) {
                int minCost = INF;
                // enumerate all proper divisor
                for (int d = 1; d * d <= len; d++) {
                    if (len % d == 0) {
                        // smaller divisor
                        if (d < len) minCost = Math.min (minCost, calculateCost (s, i, len, d));

                        // corresponding larger divisor
                        int other = len / d;
                        if (other != d && other < len) minCost = Math.min (minCost, calculateCost (s, i, len, other));
                    }
                }
                cost[i][i + len - 1] = minCost;
            }
        }

        // dp[p][i] = minimum cost to divide suffix starting at i into exactly p parts
        int[][] dp = new int[k + 1][n + 1];

        for (int p = 0; p <= k; p++) Arrays.fill (dp[p], INF);

        // base case: zero parts and empty suffix
        dp[0][n] = 0;

        // calculate dp states
        for (int p = 1; p <= k; p++) {
            // each remaining part needs atleast 2 characters
            for (int i = 0; i <= n - 2 * p; i++) {
                // leave atleast 2 * (p - 1) characters for remaining parts
                int maxEnd = n - 2 * (p - 1) - 1;
                for (int j = i + 1; j <= maxEnd; j++) {
                    if (dp[p - 1][j + 1] == INF) continue;
                    dp[p][i] = Math.min (dp[p][i], cost[i][j] + dp[p - 1][j + 1]);
                }
            }
        }

        return dp[k][0];    
    }
}