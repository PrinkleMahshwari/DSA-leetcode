#include <string.h>
#include <stdlib.h>

char* removeOuterParentheses(char* s) {
    int len = strlen(s);
    char* result = (char*)malloc(sizeof(char) * (len + 1));
    int resIdx = 0;
    int depth = 0;

    for (int i = 0; s[i] != '\0'; i++) {
        char ch = s[i];
        if (ch == '(') {
            if (depth > 0) {
                result[resIdx++] = ch;
            }
            depth++;
        } else {
            depth--;
            if (depth > 0) {
                result[resIdx++] = ch;
            }
        }
    }

    result[resIdx] = '\0'; // Null-terminate the string
    return result;
}
