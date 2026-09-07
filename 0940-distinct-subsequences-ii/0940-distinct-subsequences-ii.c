#include <string.h>

int distinctSubseqII(char* s) {
    long long MOD = 1000000007;

    long long dp = 1;
    long long last[26] = {0}; // Initialize all 26 elements to 0
    int n = strlen(s);

    for (int i = 0; i < n; i++) {
        int index = s[i] - 'a';

        long long previous = dp;
        // C modulo operator can yield negative values, so we add MOD before taking % MOD
        dp = (2 * dp - last[index] + MOD) % MOD;
        last[index] = previous;
    }

    return (int)((dp - 1 + MOD) % MOD);
}
