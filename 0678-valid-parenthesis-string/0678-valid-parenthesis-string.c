#include <stdbool.h>
#include <string.h>

int max_val(int a, int b) {
    return a > b ? a : b;
}

bool checkValidString(char* s) {
    int low = 0;
    int high = 0;
    int len = strlen(s);

    for (int i = 0; i < len; i++) {
        char ch = s[i];

        if (ch == '(') {
            low++;
            high++;
        } else if (ch == ')') {
            low--;
            high--;
        } else {
            // '*'
            low--;
            high++;
        }

        // minimum balance cannot be negative
        low = max_val(0, low);

        // even the maximum balance is negative
        if (high < 0) return false;
    }

    return low == 0;
}
