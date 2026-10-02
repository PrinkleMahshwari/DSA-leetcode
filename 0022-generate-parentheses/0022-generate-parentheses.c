#include <stdlib.h>
#include <string.h>

void backtrack(int n, int open, int close, char* current, int currentLen, char** result, int* resultCount) {
    // complete valid combination
    if (currentLen == 2 * n) {
        current[currentLen] = '\0';
        result[*resultCount] = strdup(current);
        (*resultCount)++;
        return;
    }

    // add opening parenthesis
    if (open < n) {
        current[currentLen] = '(';
        backtrack(n, open + 1, close, current, currentLen + 1, result, resultCount);
    }

    // add closing parenthesis only when valid
    if (close < open) {
        current[currentLen] = ')';
        backtrack(n, open, close + 1, current, currentLen + 1, result, resultCount);
    }
}

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
char** generateParenthesis(int n, int* returnSize) {
    // Upper bound for Catalan combinations count allocation safety (Max n=8 constraint yields 1430)
    int maxCombinations = 5000; 
    char** result = (char**)malloc(maxCombinations * sizeof(char*));
    char* current = (char*)malloc((2 * n + 1) * sizeof(char));
    
    int resultCount = 0;
    backtrack(n, 0, 0, current, 0, result, &resultCount);

    free(current);
    *returnSize = resultCount;
    return result;
}
