#include <stdbool.h>

int totalNumbers(int* digits, int digitsSize) {
    int freq[10] = {0};

    for (int i = 0; i < digitsSize; i++) {
        freq[digits[i]]++;
    }
    
    int count = 0;

    for (int num = 100; num <= 999; num++) {
        // Skip odd numbers
        if ((num & 1) != 0) {
            continue;
        }
        
        int a = num / 100;
        int b = (num / 10) % 10;
        int c = num % 10;

        int used[10] = {0};
        used[a]++;
        used[b]++;
        used[c]++;

        bool possible = true;

        for (int d = 0; d <= 9; d++) {
            if (used[d] > freq[d]) {
                possible = false;
                break;
            }
        }

        if (possible) {
            count++;
        }
    }

    return count;
}
