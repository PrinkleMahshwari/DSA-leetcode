#include <stdbool.h>
#include <stdlib.h>

bool winnerSquareGame(int n) {
    // Allocate heap memory for the dp state tracking array
    bool* dp = (bool*)calloc(n + 1, sizeof(bool));
    
    dp[0] = false;
    
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j * j <= i; j++) {
            int square = j * j;
            
            if (!dp[i - square]) {
                dp[i] = true;
                break;
            }
        }
    }
    
    bool result = dp[n];
    
    // Clean up allocated heap memory space to prevent memory leaks
    free(dp);
    
    return result;
}
