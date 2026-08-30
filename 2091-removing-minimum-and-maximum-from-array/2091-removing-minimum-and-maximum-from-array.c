#include <stdlib.h>

int min_val(int a, int b) {
    return a < b ? a : b;
}

int max_val(int a, int b) {
    return a > b ? a : b;
}

int minimumDeletions(int* nums, int numsSize) {
    int n = numsSize;

    int minIndex = 0;
    int maxIndex = 0;

    // find min and max indices 
    for (int i = 0; i < n; i++) {
        if (nums[i] < nums[minIndex]) {
            minIndex = i;
        }
        
        if (nums[i] > nums[maxIndex]) {
            maxIndex = i;
        }
    }

    int left = min_val(minIndex, maxIndex);
    int right = max_val(minIndex, maxIndex);

    // remove both from the front
    int fromFront = right + 1;

    // remove both from the back
    int fromBack = n - left;

    // remove one from front, and one from back
    int fromBoth = (left + 1) + (n - right);

    return min_val(fromFront, min_val(fromBack, fromBoth));
}
