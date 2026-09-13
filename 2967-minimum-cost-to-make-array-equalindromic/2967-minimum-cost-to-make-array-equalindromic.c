#include <stdlib.h>
#include <stdbool.h>
#include <math.h>

// Sort comparator function
int compare(const void* a, const void* b) {
    return (*(int*)a - *(int*)b);
}

bool isPalindrome(long long x) {
    if (x < 0) return false;
    long long original = x;
    long long reversed = 0;
    while (x > 0) {
        reversed = reversed * 10 + (x % 10);
        x /= 10;
    }
    return original == reversed;
}

long long getLowerPalindrome(long long x) {
    while (!isPalindrome(x)) {
        x--;
    }
    return x;
}

long long getUpperPalindrome(long long x) {
    while (!isPalindrome(x)) {
        x++;
    }
    return x;
}

long long calculateCost(int* nums, int numsSize, long long target) {
    long long cost = 0;
    for (int i = 0; i < numsSize; i++) {
        cost += llabs(nums[i] - target);
    }
    return cost;
}

long long minimumCost(int* nums, int numsSize) {
    // Step 1: Sort the array to find the true median
    qsort(nums, numsSize, sizeof(int), compare);
    long long median = nums[numsSize / 2];

    // Step 2: Get the closest valid palindrome numbers surrounding the median
    long long lowerPalindrome = getLowerPalindrome(median);
    long long upperPalindrome = getUpperPalindrome(median);

    // Step 3: Compute total operational cost for both candidates
    long long costWithLower = calculateCost(nums, numsSize, lowerPalindrome);
    long long costWithUpper = calculateCost(nums, numsSize, upperPalindrome);

    // Step 4: Return the minimal absolute operation cost
    return costWithLower < costWithUpper ? costWithLower : costWithUpper;
}
