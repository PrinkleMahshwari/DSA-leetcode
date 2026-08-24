#include <string.h>

long long max_ll(long long a, long long b) {
    return a > b ? a : b;
}

int stoneGameVIII(int* stones, int stonesSize) {
    int n = stonesSize;

    // Long long buffer allocation to handle multi-index prefix additions safely
    long long* prefix = (long long*)malloc(n * sizeof(long long));
    prefix[0] = stones[0];

    for (int i = 1; i < n; i++) {
        prefix[i] = prefix[i - 1] + stones[i];
    }

    long long ans = prefix[n - 1];

    for (int i = n - 2; i >= 1; i--) {
        ans = max_ll(ans, prefix[i] - ans);
    }

    int result = (int)ans;
    free(prefix); // Clean up memory directly

    return result;
}
