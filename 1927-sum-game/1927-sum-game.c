#include <stdbool.h>
#include <string.h>

bool sumGame(char* num) {
    int n = strlen(num);
    int half = n / 2;

    int leftSum = 0;
    int rightSum = 0;

    int qLeft = 0;
    int qRight = 0;

    for (int i = 0; i < half; i++) {
        char c = num[i];

        if (c == '?') {
            qLeft++;
        } else {
            leftSum += c - '0';
        }
    }

    for (int i = half; i < n; i++) {
        char c = num[i];

        if (c == '?') {
            qRight++;
        } else {
            rightSum += c - '0';
        }
    }

    int diff = leftSum - rightSum;

    return 2 * diff != 9 * (qRight - qLeft);
}
