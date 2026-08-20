#include <stdlib.h>

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* resultArray(int* nums, int numsSize, int* returnSize) {
    int* arr1 = (int*)malloc(numsSize * sizeof(int));
    int* arr2 = (int*)malloc(numsSize * sizeof(int));
    
    int size1 = 0;
    int size2 = 0;

    // first two operations are fixed
    arr1[size1++] = nums[0];
    arr2[size2++] = nums[1];

    int last1 = nums[0];
    int last2 = nums[1];

    // process remaining elements
    for (int i = 2; i < numsSize; i++) {
        if (last1 > last2) {
            arr1[size1++] = nums[i];
            last1 = nums[i];
        } else {
            arr2[size2++] = nums[i];
            last2 = nums[i];
        }
    }

    // concatenate arr1 and arr2 into a single result array
    int* result = (int*)malloc(numsSize * sizeof(int));
    int index = 0;

    for (int i = 0; i < size1; i++) {
        result[index++] = arr1[i];
    }
    for (int i = 0; i < size2; i++) {
        result[index++] = arr2[i];
    }

    // clean up temporary dynamic array allocations
    free(arr1);
    free(arr2);

    *returnSize = numsSize;
    return result;
}
