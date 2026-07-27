#include <stdlib.h>

// Comparison function for sorting integers safely
int compareInts(const void* a, const void* b) {
    int valA = *(const int*)a;
    int valB = *(const int*)b;
    if (valA < valB) return -1;
    if (valA > valB) return 1;
    return 0;
}

int longestConsecutive(int* nums, int numsSize) {
    if (numsSize == 0) return 0;

    // Step 1: Sort the array in-place
    qsort(nums, numsSize, sizeof(int), compareInts);

    int longest = 1;
    int currentStreak = 1;

    // Step 2: Linear scan to find the longest sequence chain
    for (int i = 1; i < numsSize; i++) {
        if (nums[i] != nums[i - 1]) { // Skip identical duplicates
            if (nums[i] == nums[i - 1] + 1) {
                currentStreak++;
            } else {
                if (currentStreak > longest) {
                    longest = currentStreak;
                }
                currentStreak = 1; // Reset streak
            }
        }
    }

    return currentStreak > longest ? currentStreak : longest;
}
