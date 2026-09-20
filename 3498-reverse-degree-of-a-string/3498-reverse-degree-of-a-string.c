#include <string.h>

int reverseDegree(char* s) {
    int result = 0;
    int len = strlen(s);

    for (int i = 0; i < len; i++) {
        int reverseValue = 26 - (s[i] - 'a');
        result += reverseValue * (i + 1);
    }

    return result;
}
