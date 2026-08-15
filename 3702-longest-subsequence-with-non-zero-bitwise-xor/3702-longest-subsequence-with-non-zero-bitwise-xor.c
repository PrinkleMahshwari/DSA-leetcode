#include <stdbool.h>

int longestSubsequence(int* nums, int numsSize) {
    int xor_val = 0; // renamed from 'xor' to avoid C++ keyword conflicts in some compilers
    bool hasNonZero = false;

    for (int i = 0; i < numsSize; i++) {
        int num = nums[i];
        xor_val ^= num;

        if (num != 0) {
            hasNonZero = true;
        }
    }

    if (xor_val != 0) {
        return numsSize;
    }

    return hasNonZero ? numsSize - 1 : 0;
}
