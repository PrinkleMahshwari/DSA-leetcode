#include <stdbool.h>
#include <limits.h>

int min_val(int a, int b) {
    return a < b ? a : b;
}

bool uniformArray(int* nums1, int nums1Size) {
    int smallestOdd = INT_MAX;
    int smallestEven = INT_MAX;

    for (int i = 0; i < nums1Size; i++) {
        int num = nums1[i];
        if ((num & 1) == 0) {
            smallestEven = min_val(smallestEven, num);
        } else {
            smallestOdd = min_val(smallestOdd, num);
        }
    }

    // all elements already have the same parity
    if (smallestOdd == INT_MAX || smallestEven == INT_MAX) {
        return true;
    }
    
    // mixed parity: make everything odd
    return smallestOdd < smallestEven;
}
