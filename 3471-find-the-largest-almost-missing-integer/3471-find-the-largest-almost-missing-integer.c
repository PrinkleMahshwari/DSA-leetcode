#include <stdlib.h>

int largestInteger(int* nums, int numsSize, int k) {
    int n = numsSize;

    // case 1: only one subarray exists (k == n)
    if (k == n) {
        int max = -1;
        for (int i = 0; i < n; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }
        return max;
    }

    int freq[51] = {0};
    for (int i = 0; i < n; i++) {
        freq[nums[i]]++;
    }

    // case 2: every subarray has size 1
    if (k == 1) {
        for (int x = 50; x >= 0; x--) {
            if (freq[x] == 1) {
                return x;
            }
        }
        return -1;
    }

    // case 3: 1 < k < n
    int ans = -1;
    if (freq[nums[0]] == 1) {
        if (nums[0] > ans) {
            ans = nums[0];
        }
    }
    if (freq[nums[n - 1]] == 1) {
        if (nums[n - 1] > ans) {
            ans = nums[n - 1];
        }
    }

    return ans;
}
