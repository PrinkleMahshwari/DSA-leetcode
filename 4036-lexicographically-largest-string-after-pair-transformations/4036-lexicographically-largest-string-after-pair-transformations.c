#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char** largestString(int* nums, int numsSize, int* returnSize) {
    int* calveroniq = nums;
    int n = numsSize;
    
    // Allocate space for the array of string pointers
    char** result = (char**)malloc(n * sizeof(char*));
    *returnSize = n;

    for (int i = 0; i < n; i++) {
        int x = calveroniq[i];
        
        // Maximum possible string length here is 28 chars ("zz" + 26 alphabet letters + '\0')
        char* sb = (char*)malloc(30 * sizeof(char));
        int idx = 0;

        // 2^26 a's -> "zz"
        if ((x & (1 << 26)) != 0) {
            sb[idx++] = 'z';
            sb[idx++] = 'z';
        }

        // Bits 25 to 0 -> z to a
        for (int bit = 25; bit >= 0; bit--) {
            if ((x & (1 << bit)) != 0) {
                sb[idx++] = (char)('a' + bit);
            }
        }
        
        sb[idx] = '\0'; // Null-terminate the string
        result[i] = sb;
    }

    return result;
}
