#include <stdio.h>

int smallestNumber(int n, int t) {
    for (int num = n; num <= n + 10; num++) {
        int product = 1;
        int temp = num;
        
        if (temp == 0) {
            product = 0;
        }
        
        while (temp > 0) {
            product *= temp % 10;
            temp /= 10;
        }
        
        if (product % t == 0) {
            return num;
        }
    }
    return -1; 
}
