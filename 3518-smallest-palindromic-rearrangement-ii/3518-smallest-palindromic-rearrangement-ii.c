#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// Helper to calculate multinomial coefficient capped at threshold
long long getPermutations(int* counts, int total, int threshold) {
    long long res = 1;
    int currentTotal = 1;
    
    for (int i = 0; i < 26; i++) {
        int cnt = counts[i];
        for (int j = 1; j <= cnt; j++) {
            res = res * currentTotal / j;
            currentTotal++;
            if (res > threshold) {
                return (long long)threshold + 1; // Cap to avoid integer overflow
            }
        }
    }
    return res;
}

char* smallestPalindrome(char* s, int k) {
    int n = strlen(s);
    int totalCounts[26] = {0};
    for (int i = 0; i < n; i++) {
        totalCounts[s[i] - 'a']++;
    }
    
    int halfLen = n / 2;
    int halfCounts[26] = {0};
    char midChar = 0;
    
    for (int i = 0; i < 26; i++) {
        halfCounts[i] = totalCounts[i] / 2;
        if (totalCounts[i] % 2 != 0) {
            midChar = (char)(i + 'a');
        }
    }
    
    char* firstHalf = (char*)malloc(halfLen * sizeof(char));
    int remainingSlots = halfLen;
    
    // Build the first half position by position
    for (int pos = 0; pos < halfLen; pos++) {
        bool found = false;
        for (int c = 0; c < 26; c++) {
            if (halfCounts[c] > 0) {
                halfCounts[c]--;
                remainingSlots--;
                
                long long p = getPermutations(halfCounts, remainingSlots, k);
                
                if (p >= k) {
                    firstHalf[pos] = (char)(c + 'a');
                    found = true;
                    break;
                } else {
                    k -= p;
                    halfCounts[c]++;
                    remainingSlots++;
                }
            }
        }
        if (!found) {
            free(firstHalf);
            return "";
        }
    }
    
    if (k > 1) {
        free(firstHalf);
        return "";
    }
    
    // Reconstruct full mirror buffer string
    char* result = (char*)malloc((n + 1) * sizeof(char));
    result[n] = '\0';
    
    for (int i = 0; i < halfLen; i++) {
        result[i] = firstHalf[i];
        result[n - 1 - i] = firstHalf[i];
    }
    if (n % 2 != 0) {
        result[halfLen] = midChar;
    }
    
    free(firstHalf);
    return result;
}
