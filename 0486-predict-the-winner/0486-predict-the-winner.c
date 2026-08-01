#include <stdbool.h>

#define MAX(a, b) ((a) > (b) ? (a) : (b))

bool predictTheWinner(int* nums, int numsSize) {
    // Dynamic stack-allocated VLA array mapping matrix bounds instantly
    int dp[numsSize][numsSize];

    // Base case initialization
    for (int i = 0; i < numsSize; i++) {
        dp[i][i] = nums[i];
    }

    // Filling dp table for increasing subarray lengths
    for (int len = 2; len <= numsSize; len++) {
        for (int i = 0; i <= numsSize - len; i++) {
            int j = i + len - 1;
            
            dp[i][j] = MAX(
                nums[i] - dp[i + 1][j], // Picking left end
                nums[j] - dp[i][j - 1]  // Picking right end
            );
        }
    }

    return dp[0][numsSize - 1] >= 0;
}
