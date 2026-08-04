#include <stdlib.h>
#include <stdbool.h>

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* findMissingElements(int* nums, int numsSize, int* returnSize) {
    if (numsSize == 0) {
        *returnSize = 0;
        return NULL;
    }

    // Finding min and max in one pass
    int min = nums[0];
    int max = nums[0];
    for (int i = 1; i < numsSize; i++) {
        if (nums[i] < min) min = nums[i];
        if (nums[i] > max) max = nums[i];
    }

    int range = max - min + 1;
    
    // Allocate primitive boolean tracking buffer map
    bool* present = (bool*)calloc(range, sizeof(bool));
    for (int i = 0; i < numsSize; i++) {
        present[nums[i] - min] = true;
    }

    // Pre-calculate upper bound size needed for missing items to allocate exactly
    int maxPossibleMissing = range > 2 ? range - 2 : 0;
    int* tempResult = (int*)malloc(maxPossibleMissing * sizeof(int));
    int missingCount = 0;

    // Collecting missing elements in sorted order
    for (int i = 1; i < range - 1; i++) {
        if (!present[i]) {
            tempResult[missingCount++] = i + min;
        }
    }

    // Shrink allocation down to the exact match size found
    *returnSize = missingCount;
    int* result = (int*)malloc(missingCount * sizeof(int));
    for (int i = 0; i < missingCount; i++) {
        result[i] = tempResult[i];
    }

    free(present);
    free(tempResult);
    return result;
}
