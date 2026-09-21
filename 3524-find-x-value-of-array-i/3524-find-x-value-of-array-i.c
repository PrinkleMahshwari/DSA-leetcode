#include <stdlib.h>
#include <string.h>

long long* resultArray(int* nums, int numsSize, int k, int* returnSize) {
    long long* answer = (long long*)calloc(k, sizeof(long long));
    long long* dp = (long long*)calloc(k, sizeof(long long));

    for (int i = 0; i < numsSize; i++) {
        int value = nums[i] % k;
        long long* next = (long long*)calloc(k, sizeof(long long));

        // start a new subarray with nums[i]
        next[value]++;

        // extend every subarray ending at the previous index
        for (int r = 0; r < k; r++) {
            if (dp[r] > 0) {
                int newRemainder = (r * value) % k;
                next[newRemainder] += dp[r];
            }
        }

        // add all subarrays ending at this index
        for (int r = 0; r < k; r++) {
            answer[r] += next[r];
        }
        
        free(dp);
        dp = next;
    }

    free(dp);
    *returnSize = k;
    return answer;
}
