#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX(a, b) ((a) > (b) ? (a) : (b))

// Comparator function to sort intervals by right boundary
int compareIntervals(const void* a, const void* b) {
    int r1 = ((int*)a)[1];
    int r2 = ((int*)b)[1];
    return r1 - r2;
}

char** maxNumOfSubstrings(char* s, int* returnSize) {
    int n = strlen(s);

    int first[26];
    int last[26];
    for (int i = 0; i < 26; i++) {
        first[i] = n;
        last[i] = -1;
    }

    // Find first and last occurrence of every character
    for (int i = 0; i < n; i++) {
        int c = s[i] - 'a';
        if (first[c] == n) first[c] = i;
        last[c] = i;
    }

    // Maximum possible unique valid intervals is 26
    int intervals[26][2];
    int intervalCount = 0;

    // Build every valid minimal interval
    for (int c = 0; c < 26; c++) {
        if (last[c] == -1) continue;

        int left = first[c];
        int right = last[c];
        bool valid = true;

        for (int i = left; i <= right; i++) {
            int current = s[i] - 'a';

            // This character appeared before our left boundary.
            if (first[current] < left) {
                valid = false;
                break;
            }

            // Need to include all occurrences of this character.
            right = MAX(right, last[current]);
        }

        if (valid) {
            intervals[intervalCount][0] = left;
            intervals[intervalCount][1] = right;
            intervalCount++;
        }
    }

    // Sort intervals by ending position
    qsort(intervals, intervalCount, sizeof(intervals[0]), compareIntervals);

    // Collect result substrings
    char** answer = (char**)malloc(26 * sizeof(char*));
    int ansCount = 0;
    int end = -1;

    for (int i = 0; i < intervalCount; i++) {
        int left = intervals[i][0];
        int right = intervals[i][1];

        if (left > end) {
            int len = right - left + 1;
            answer[ansCount] = (char*)malloc((len + 1) * sizeof(char));
            strncpy(answer[ansCount], s + left, len);
            answer[ansCount][len] = '\0';
            ansCount++;
            end = right;
        }
    }

    *returnSize = ansCount;
    return answer;
}
