#include <stdlib.h>
#include <stdio.h>

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
char** summaryRanges(int* nums, int numsSize, int* returnSize) {
    // Return empty results immediately if size boundary is zero
    if (numsSize == 0) {
        *returnSize = 0;
        return NULL;
    }

    // Allocate array capacity for the string pointer results
    char** result = (char**)malloc(numsSize * sizeof(char*));
    int count = 0;
    int i = 0;

    while (i < numsSize) {
        int start = nums[i];

        while (i + 1 < numsSize && nums[i + 1] == nums[i] + 1) {
            i++;
        }

        // Allocate local buffer bounds (max 25 bytes per formatted range string)
        result[count] = (char*)malloc(25 * sizeof(char));

        if (start == nums[i]) {
            sprintf(result[count], "%d", start);
        } else {
            sprintf(result[count], "%d->%d", start, nums[i]);
        }

        count++;
        i++;
    }

    *returnSize = count;
    return result;
}
