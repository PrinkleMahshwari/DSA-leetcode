#include <stdlib.h>
#include <string.h>

int max_value(int a, int b) {
    return a > b ? a : b;
}

int stoneGameV(int* stoneValue, int stoneValueSize) {
    int n = stoneValueSize;

    // Prefix sums initialization
    int* prefix = (int*)calloc(n + 1, sizeof(int));
    for (int i = 0; i < n; i++) {
        prefix[i + 1] = prefix[i] + stoneValue[i];
    }

    // Dynamic allocation of 2D DP matrix to safely accommodate variable constraints
    int** dp = (int**)malloc(n * sizeof(int*));
    for (int i = 0; i < n; i++) {
        dp[i] = (int*)calloc(n, sizeof(int));
    }

    // Build intervals from smaller to larger lengths
    for (int len = 2; len <= n; len++) {
        for (int l = 0; l + len <= n; l++) {
            int r = l + len - 1;

            for (int k = l; k < r; k++) {
                int leftSum = prefix[k + 1] - prefix[l];
                int rightSum = prefix[r + 1] - prefix[k + 1];

                if (leftSum < rightSum) {
                    dp[l][r] = max_value(dp[l][r], leftSum + dp[l][k]);
                } else if (leftSum > rightSum) {
                    dp[l][r] = max_value(dp[l][r], rightSum + dp[k + 1][r]);
                } else {
                    dp[l][r] = max_value(dp[l][r], leftSum + max_value(dp[l][k], dp[k + 1][r]));
                }
            }
        }
    }

    int result = dp[0][n - 1];

    // Clean up allocated heap memory spaces to completely prevent leaks
    for (int i = 0; i < n; i++) {
        free(dp[i]);
    }
    free(dp);
    free(prefix);

    return result;
}
