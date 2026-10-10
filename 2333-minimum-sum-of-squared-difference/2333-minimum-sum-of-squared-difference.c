#include <stdlib.h>
#include <math.h>

long long min_long(long long a, long long b) {
    return a < b ? a : b;
}

long long minSumSquareDiff(int* nums1, int nums1Size, int* nums2, int nums2Size, int k1, int k2) {
    int n = nums1Size;
    int* diff = (int*)malloc(n * sizeof(int));

    int maxDiff = 0;
    long long totalDiff = 0;

    for (int i = 0; i < n; i++) {
        diff[i] = abs(nums1[i] - nums2[i]);
        if (diff[i] > maxDiff) maxDiff = diff[i];
        totalDiff += diff[i];
    }

    long long k = (long long)k1 + k2;

    // If all differences can become zero
    if (k >= totalDiff) {
        free(diff);
        return 0;
    }

    // Binary search for the smallest possible max difference
    int low = 0;
    int high = maxDiff;

    while (low < high) {
        int mid = low + (high - low) / 2;
        long long needed = 0;

        for (int i = 0; i < n; i++) {
            if (diff[i] > mid) needed += diff[i] - mid;
        }

        if (needed <= k) high = mid;
        else low = mid + 1;
    }

    int limit = low;
    long long needed = 0;
    long long result = 0;

    for (int i = 0; i < n; i++) {
        long long reduced = diff[i] < limit ? diff[i] : limit;
        needed += diff[i] - reduced;
        result += reduced * reduced;
    }

    // Use remaining operations to reduce some differences by one more
    long long remaining = k - needed;
    result -= remaining * (2LL * limit - 1);

    free(diff);
    return result;
}
