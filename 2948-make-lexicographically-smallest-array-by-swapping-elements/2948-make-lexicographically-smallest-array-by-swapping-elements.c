#include <stdlib.h>
#include <string.h>

// Struct definition to keep structure matching flat double-arrays safely
typedef struct {
    int value;
    int index;
} Element;

// Comparator helper to sort elements by value
int compareElements(const void* a, const void* b) {
    int valA = ((Element*)a)->value;
    int valB = ((Element*)b)->value;
    if (valA < valB) return -1;
    if (valA > valB) return 1;
    return 0;
}

// Comparator helper to sort raw indices ascending
int compareInts(const void* a, const void* b) {
    return (*(int*)a - *(int*)b);
}

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* lexicographicallySmallestArray(int* nums, int numsSize, int limit, int* returnSize) {
    int n = numsSize;
    
    Element* arr = (Element*)malloc(n * sizeof(Element));
    for (int i = 0; i < n; i++) {
        arr[i].value = nums[i];
        arr[i].index = i;
    }

    // Sort values ascending via qsort
    qsort(arr, n, sizeof(Element), compareElements);

    int* result = (int*)malloc(n * sizeof(int));
    int start = 0;

    // Buffer to temporarily pull indices per group iteration
    int* indices = (int*)malloc(n * sizeof(int));

    while (start < n) {
        int end = start;

        // Avoid integer overflow by casting subtraction operands safely
        while (end + 1 < n && (long long)arr[end + 1].value - arr[end].value <= limit) {
            end++;
        }

        int size = end - start + 1;

        // Collect and isolate original indices mapping positions
        for (int i = 0; i < size; i++) {
            indices[i] = arr[start + i].index;
        }

        // Sort indices array group
        qsort(indices, size, sizeof(int), compareInts);

        // Map values into final output positions
        for (int i = 0; i < size; i++) {
            result[indices[i]] = arr[start + i].value;
        }

        start = end + 1;
    }

    free(arr);
    free(indices);

    *returnSize = n;
    return result;
}
