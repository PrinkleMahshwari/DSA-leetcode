#include <stdlib.h>

#define MAX(a, b) ((a) > (b) ? (a) : (b))
#define MIN(a, b) ((a) < (b) ? (a) : (b))

int firstStableIndex(int* nums, int numsSize, int k) {
    if (numsSize == 0) return -1;

    int* prefixMax = (int*)malloc(numsSize * sizeof(int));
    int* suffMin = (int*)malloc(numsSize * sizeof(int));

    // Compute prefix maximums
    prefixMax[0] = nums[0];
    for (int i = 1; i < numsSize; i++) {
        prefixMax[i] = MAX(prefixMax[i - 1], nums[i]);
    }

    // Compute suffix minimums
    suffMin[numsSize - 1] = nums[numsSize - 1];
    for (int i = numsSize - 2; i >= 0; i--) {
        suffMin[i] = MIN(suffMin[i + 1], nums[i]);
    }

    // Find smallest stable index
    int answer = -1;
    for (int i = 0; i < numsSize; i++) {
        if (prefixMax[i] - suffMin[i] <= k) {
            answer = i;
            break;
        }
    }

    // Free dynamically allocated arrays to prevent memory leaks
    free(prefixMax);
    free(suffMin);

    return answer;
}
