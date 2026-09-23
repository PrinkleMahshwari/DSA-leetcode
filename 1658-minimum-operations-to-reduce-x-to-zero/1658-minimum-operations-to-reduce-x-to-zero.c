#include <stdlib.h>

int minOperations(int* nums, int numsSize, int x) {
    int n = numsSize;

    long long total = 0; // Guard against integer overflow on wide arrays
    for (int i = 0; i < n; i++) total += nums[i];

    long long target = total - x;

    if (target < 0) return -1;
    if (target == 0) return n;

    int left = 0;
    long long sum = 0;
    int maxLength = -1;

    for (int right = 0; right < n; right++) {
        sum += nums[right];

        while (left <= right && sum > target) {
            sum -= nums[left++];
        }

        if (sum == target) {
            int currentLen = right - left + 1;
            if (currentLen > maxLength) {
                maxLength = currentLen;
            }
        }
    }

    return maxLength == -1 ? -1 : n - maxLength;
}
