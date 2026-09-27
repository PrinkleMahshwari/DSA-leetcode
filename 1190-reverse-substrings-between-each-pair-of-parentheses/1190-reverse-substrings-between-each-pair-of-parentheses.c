#include <stdlib.h>
#include <string.h>

char* reverseParentheses(char* s) {
    int n = strlen(s);
    
    int* pair = (int*)calloc(n, sizeof(int));
    int* stack = (int*)malloc(n * sizeof(int));
    int stackTop = -1;

    // Find matching parentheses
    for (int i = 0; i < n; i++) {
        if (s[i] == '(') {
            stack[++stackTop] = i;
        } else if (s[i] == ')') {
            int open = stack[stackTop--];
            pair[open] = i;
            pair[i] = open;
        }
    }
    
    free(stack); // Stack is no longer needed after matching

    char* result = (char*)malloc((n + 1) * sizeof(char));
    int resIndex = 0;

    int i = 0, direction = 1;

    while (i >= 0 && i < n) {
        char current = s[i];

        if (current == '(' || current == ')') {
            // Jump to the matching parenthesis
            i = pair[i];
            // Reverse traversal direction
            direction = -direction;
        } else {
            result[resIndex++] = current;
        }

        i += direction;
    }

    result[resIndex] = '\0';
    free(pair);

    return result;
}
