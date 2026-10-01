#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

bool isValid(char* s) {
    int len = strlen(s);
    char* stack = (char*)malloc(len * sizeof(char));
    int top = -1;

    for (int i = 0; i < len; i++) {
        char ch = s[i];

        // opening brackets
        if (ch == '(' || ch == '[' || ch == '{') {
            stack[++top] = ch;
        } 
        // closing bracket
        else {
            if (top == -1) {
                free(stack);
                return false;
            }

            char open = stack[top];

            if ((ch == ')' && open != '(') || 
                (ch == ']' && open != '[') || 
                (ch == '}' && open != '{')) {
                free(stack);
                return false;
            }

            top--;
        }
    }

    bool result = (top == -1);
    free(stack);
    return result;
}
