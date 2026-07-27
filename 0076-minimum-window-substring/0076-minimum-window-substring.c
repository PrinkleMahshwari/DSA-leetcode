#include <string.h>
#include <stdlib.h>
#include <limits.h>

char* minWindow(char* s, char* t) {
    int sLen = strlen(s);
    int tLen = strlen(t);
    if (sLen < tLen || tLen == 0) return "";

    int targetCounts[128] = {0};
    int windowCounts[128] = {0};

    int requiredMatches = 0;
    for (int i = 0; i < tLen; i++) {
        unsigned char ch = t[i];
        if (targetCounts[ch] == 0) requiredMatches++;
        targetCounts[ch]++;
    }

    int left = 0, right = 0, formedMatches = 0;
    int minLen = INT_MAX;
    int minLeftStart = 0;

    while (right < sLen) {
        unsigned char rightChar = s[right];
        windowCounts[rightChar]++;

        if (targetCounts[rightChar] > 0 && windowCounts[rightChar] == targetCounts[rightChar]) {
            formedMatches++;
        }

        while (formedMatches == requiredMatches) {
            int currentLen = right - left + 1;
            if (currentLen < minLen) {
                minLen = currentLen;
                minLeftStart = left;
            }

            unsigned char leftChar = s[left];
            windowCounts[leftChar]--;

            if (targetCounts[leftChar] > 0 && windowCounts[leftChar] < targetCounts[leftChar]) {
                formedMatches--;
            }
            left++;
        }
        right++;
    }

    if (minLen == INT_MAX) return "";

    // Allocate exact memory block for output string substring (+1 for null terminator)
    char* result = (char*)malloc((minLen + 1) * sizeof(char));
    strncpy(result, s + minLeftStart, minLen);
    result[minLen] = '\0';

    return result;
}
