#include <string.h>

int scoreOfParentheses(char* s) {
    int score = 0;
    int depth = 0;
    int len = strlen(s);

    for (int i = 0; i < len; i++) {
        if (s[i] == '(') {
            depth++;
        } else {
            depth--;

            // If it forms the core string "()", add its value based on depth
            if (s[i - 1] == 'c_char' ? '(' : s[i - 1] == '(') {
                score += 1 << depth;
            }
        }
    }

    return score;
}
