#include <stdlib.h>
#include <string.h>

void helper(char* s, char** result, int* count, int start, int checkStart, char open, char close) {
    int balance = 0;
    int len = strlen(s);
    
    for (int i = checkStart; i < len; i++) {
        char ch = s[i];
        if (ch == open) balance++;
        else if (ch == close) balance--;
        
        // Found an invalid closing parenthesis
        if (balance < 0) {
            for (int j = start; j <= i; j++) {
                // Skip duplicate removals
                if (j > start && s[j] == s[j - 1]) {
                    continue;
                }
                
                // Remove this closing parenthesis
                if (s[j] == close) {
                    char* next = (char*)malloc(sizeof(char) * len);
                    strncpy(next, s, j);
                    strcpy(next + j, s + j + 1);
                    
                    helper(next, result, count, j, i, open, close);
                    free(next);
                }
            }
            return;
        }
    }
    
    // No invalid closing parentheses remain.
    // Reverse and check the opposite direction.
    char* reversed = (char*)malloc(sizeof(char) * (len + 1));
    for (int i = 0; i < len; i++) {
        reversed[i] = s[len - 1 - i];
    }
    reversed[len] = '\0';
    
    if (open == '(') {
        helper(reversed, result, count, 0, 0, ')', '(');
        free(reversed);
    } else {
        result[*count] = reversed;
        (*count)++;
    }
}

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
char** removeInvalidParentheses(char* s, int* returnSize) {
    // LeetCode problems of this constraint scale safely up to 2048 matches max.
    char** result = (char**)malloc(sizeof(char*) * 2048);
    *returnSize = 0;
    
    helper(s, result, returnSize, 0, 0, '(', ')');
    
    return result;
}
