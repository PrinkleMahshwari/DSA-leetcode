#include <stdbool.h>
#include <stdlib.h>

int missingMultiple(int* nums, int numsSize, int k) {
    // Find the max value in nums to size our lookup table dynamically
    int maxVal = 0;
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] > maxVal) {
            maxVal = nums[i];
        }
    }

    // Allocate a boolean table initialized to false
    bool* present = (bool*)calloc(maxVal + 1, sizeof(bool));
    for (int i = 0; i < numsSize; i++) {
        present[nums[i]] = true;
    }

    int multiple = k;

    // Check bounds: if multiple exceeds maxVal, it's automatically missing
    while (multiple <= maxVal && present[multiple]) {
        multiple += k;
    }

    // Clean up allocated heap memory
    free(present);

    return multiple;
}
