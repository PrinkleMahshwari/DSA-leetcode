#include <stdlib.h>
#include <string.h>

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* validSequence(char* word1, char* word2, int* returnSize) {
    int n = strlen(word1);
    int m = strlen(word2);

    // Highly efficient stack allocation or direct pointer calculations
    int* lastValidW1Idx = (int*)malloc(m * sizeof(int));
    for (int i = 0; i < m; i++) {
        lastValidW1Idx[i] = -1;
    }

    int j = m - 1;
    for (int i = n - 1; i >= 0; i--) {
        if (j >= 0 && word1[i] == word2[j]) {
            lastValidW1Idx[j] = i;
            j--;
        }
    }

    int* answer = (int*)malloc(m * sizeof(int));
    int w2Idx = 0;
    int changed = 0; // 0 represents false, 1 represents true

    for (int i = 0; i < n && w2Idx < m; i++) {
        char c1 = word1[i];
        char c2 = word2[w2Idx];

        if (c1 == c2) {
            answer[w2Idx] = i;
            w2Idx++;
        } 
        else if (!changed && (w2Idx == m - 1 || (lastValidW1Idx[w2Idx + 1] != -1 && lastValidW1Idx[w2Idx + 1] > i))) {
            answer[w2Idx] = i;
            w2Idx++;
            changed = 1;
        }
    }

    if (w2Idx < m) {
        free(lastValidW1Idx);
        free(answer);
        *returnSize = 0;
        return NULL;
    }

    free(lastValidW1Idx);
    *returnSize = m;
    return answer;
}
