#include <stdlib.h>
#include <string.h>

char* shortestBeautifulSubstring(char* s, int k) {
    int n = strlen(s);
    int* ones = (int*)malloc(n * sizeof(int));
    int onesSize = 0;

    // store positions of all 1s
    for (int i = 0; i < n; i++) {
        if (s[i] == '1') {
            ones[onesSize++] = i;
        }
    }

    // Not enough 1s
    if (onesSize < k) {
        free(ones);
        char* empty = (char*)malloc(1 * sizeof(char));
        empty[0] = '\0';
        return empty;
    }

    char* answer = NULL;
    int ansLen = 0;

    // try every possible starting 1
    for (int i = 0; i + k - 1 < onesSize; i++) {
        int start = ones[i];
        int end = ones[i + k - 1];
        int candLen = end - start + 1;

        // Allocate transient buffer space for string extraction
        char* candidate = (char*)malloc((candLen + 1) * sizeof(char));
        strncpy(candidate, s + start, candLen);
        candidate[candLen] = '\0';

        if (answer == NULL 
                || candLen < ansLen 
                || (candLen == ansLen && strcmp(candidate, answer) < 0)) {
            if (answer != NULL) {
                free(answer);
            }
            answer = candidate;
            ansLen = candLen;
        } else {
            free(candidate);
        }
    }

    free(ones);

    // If no answer was populated, return a valid blank buffer safely
    if (answer == NULL) {
        answer = (char*)malloc(1 * sizeof(char));
        answer[0] = '\0';
    }

    return answer;
}
