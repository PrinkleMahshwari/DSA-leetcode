#include <stdlib.h>
#include <string.h>

int longestValidParentheses(char* s) {
    int n = strlen(s);
    int maxLength = 0;
    
    int* stack = (int*)malloc((n + 1) * sizeof(int));
    int top = 0;

    // boundary before the string starts
    stack[top] = -1;

    for (int i = 0; i < n; i++) {
        if (s[i] == '(') {
            stack[++top] = i;
        } else {
            top--;

            // unmatched ')'
            if (top < 0) {
                top = 0;
                stack[top] = i;
            } else {
                int currentLen = i - stack[top];
                if (currentLen > maxLength) {
                    maxLength = currentLen;
                }
            }
        }
    }

    free(stack);
    return maxLength;
}
