#include <string.h>
#include <stdlib.h>

int numDistinct(char* s, char* t) {
    int n = strlen(s);
    int m = strlen(t);

    // Use unsigned long long to avoid integer overflow errors during summation
    unsigned long long* dp = (unsigned long long*)calloc(m + 1, sizeof(unsigned long long));
    dp[0] = 1;

    for (int i = 0; i < n; i++) {
        for (int j = m; j >= 1; j--) {
            if (s[i] == t[j - 1]) {
                dp[j] += dp[j - 1];
            }
        }
    }

    int result = (int)dp[m];
    free(dp); // Prevent memory leaks
    return result;
}
