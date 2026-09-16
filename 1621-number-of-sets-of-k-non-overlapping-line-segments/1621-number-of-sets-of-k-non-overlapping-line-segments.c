#include <stdlib.h>
#include <string.h>

int numberOfSets(int n, int k) {
    long long MOD = 1000000007;

    long long* dp = (long long*)malloc(n * sizeof(long long));
    for (int i = 0; i < n; i++) {
        dp[i] = 1;
    }

    for (int j = 1; j <= k; j++) {
        long long* next_dp = (long long*)calloc(n, sizeof(long long));
        long long sum = 0;

        for (int i = 1; i < n; i++) {
            sum = (sum + dp[i - 1]) % MOD;
            next_dp[i] = (next_dp[i - 1] + sum) % MOD;
        }
        
        free(dp);
        dp = next_dp;
    }

    int result = (int)dp[n - 1];
    free(dp);
    return result;
}
