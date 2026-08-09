#include <stdlib.h>
#include <string.h>

int max_val(int a, int b) { return a > b ? a : b; }

int solve(int i, int m, int n, int* suffix, int** dp) {
    // Base case: No piles left
    if (i >= n) return 0;
    
    // Take all remaining if within reach
    if (2 * m >= n - i) return suffix[i];
    
    // Return cached results
    if (dp[i][m] != 0) return dp[i][m];
    
    int best = 0;
    for (int x = 1; x <= 2 * m; x++) {
        int nextM = max_val(m, x);
        int opponent = solve(i + x, nextM, n, suffix, dp);
        int current = suffix[i] - opponent;
        best = max_val(best, current);
    }
    
    dp[i][m] = best;
    return best;
}

int stoneGameII(int* piles, int pilesSize) {
    int n = pilesSize;
    
    // Allocate 2D DP array dynamically
    int** dp = (int**)malloc(n * sizeof(int*));
    for (int i = 0; i < n; i++) {
        dp[i] = (int*)calloc((n + 1), sizeof(int));
    }
    
    // Allocate suffix sum array
    int* suffix = (int*)calloc((n + 1), sizeof(int));
    for (int i = n - 1; i >= 0; i--) {
        suffix[i] = suffix[i + 1] + piles[i];
    }
    
    int ans = solve(0, 1, n, suffix, dp);
    
    // Clean up allocated memory spaces
    for (int i = 0; i < n; i++) {
        free(dp[i]);
    }
    free(dp);
    free(suffix);
    
    return ans;
}
