#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

#define MAX(a, b) ((a) > (b) ? (a) : (b))

int maxPalindromes(char* s, int k) {
    int n = strlen(s);

    // Dynamic allocation for 2D boolean palindrome array
    bool** palindrome = (bool**)malloc(n * sizeof(bool*));
    for (int i = 0; i < n; i++) {
        palindrome[i] = (bool*)calloc(n, sizeof(bool));
    }

    // build palindrome table
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            if (s[i] == s[j] && (j - i <= 2 || palindrome[i + 1][j - 1])) {
                palindrome[i][j] = true;
            }
        }
    }

    // dp[i] = max number of palindromes in s[0 ... i - 1]
    int* dp = (int*)calloc(n + 1, sizeof(int));

    for (int i = 1; i <= n; i++) {
        // don't use a palindrome ending at i - 1
        dp[i] = dp[i - 1];

        // try every palindrome ending at i - 1
        for (int j = 0; j < i; j++) {
            if (i - j >= k && palindrome[j][i - 1]) {
                dp[i] = MAX(dp[i], dp[j] + 1);
            }
        }
    }

    int result = dp[n];

    // Free memory allocations
    for (int i = 0; i < n; i++) {
        free(palindrome[i]);
    }
    free(palindrome);
    free(dp);

    return result;
}
