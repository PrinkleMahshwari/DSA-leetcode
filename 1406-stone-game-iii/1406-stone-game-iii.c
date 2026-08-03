#include <string.h>
#include <limits.h>

#define MAX(a, b) ((a) > (b) ? (a) : (b))

char* stoneGameIII(int* stoneValue, int stoneValueSize) {
    int n = stoneValueSize;
    // Rolling 4-slot buffer to bypass all dynamic heap allocations
    int dp[4] = {0}; 

    for (int i = n - 1; i >= 0; i--) {
        int currentIdx = i % 4;
        dp[currentIdx] = INT_MIN; // Native 32-bit minimum integer value
        int take = 0;

        for (int k = 0; k < 3 && i + k < n; k++) {
            take += stoneValue[i + k];
            dp[currentIdx] = MAX(dp[currentIdx], take - dp[(i + k + 1) % 4]);
        }
    }

    int relativeDiff = dp[0];
    if (relativeDiff > 0) return "Alice";
    if (relativeDiff < 0) return "Bob";
    return "Tie";
}
