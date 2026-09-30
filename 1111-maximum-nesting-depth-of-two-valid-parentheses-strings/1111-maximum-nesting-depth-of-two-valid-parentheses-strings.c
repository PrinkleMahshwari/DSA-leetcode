#include <stdlib.h>
#include <string.h>

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* maxDepthAfterSplit(char* seq, int* returnSize) {
    int n = strlen(seq);
    int* answer = (int*)malloc(n * sizeof(int));
    *returnSize = n;

    int depth = 0;

    for (int i = 0; i < n; i++) {
        if (seq[i] == '(') {
            depth++;
            answer[i] = depth & 1;
        } else {
            answer[i] = depth & 1;
            depth--;
        }
    }

    return answer;
}
