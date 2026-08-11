#include <stdbool.h>

int missingInteger(int* nums, int numsSize) {
    int prefixSum = nums[0];
    
    // Step 1: Calculate the sum of the longest sequential prefix
    for (int i = 1; i < numsSize; i++) {
        if (nums[i] == nums[i - 1] + 1) {
            prefixSum += nums[i];
        } else {
            break;
        }
    }
    
    // Step 2: Create a direct lookup boolean array for tracking number presence
    // Sized to 1300 to securely hold the maximum possible sequential sum (50 * 51 / 2 = 1275)
    bool lookup[1300] = {false};
    for (int i = 0; i < numsSize; i++) {
        // Only track values within bounds of our array tracker
        if (nums[i] < 1300) {
            lookup[nums[i]] = true;
        }
    }
    
    // Step 3: Increment prefixSum until it's missing from the lookup table
    while (prefixSum < 1300 && lookup[prefixSum]) {
        prefixSum++;
    }
    
    return prefixSum;
}
