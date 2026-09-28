#include <string.h>

int maxDepth(char* s) {
    int depth = 0;
    int maxDepth = 0;
    int len = strlen(s);

    for (int i = 0; i < len; i++) {
        char ch = s[i];
        if (ch == '(') {
            depth++;
            if (depth > maxDepth) {
                maxDepth = depth;
            }
        } else if (ch == ')') {
            depth--;
        }
    }

    return maxDepth;
}
