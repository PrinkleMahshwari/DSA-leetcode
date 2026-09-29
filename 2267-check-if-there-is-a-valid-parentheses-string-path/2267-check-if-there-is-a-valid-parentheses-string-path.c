#include <stdbool.h>
#include <stdlib.h>

bool hasValidPath(char** grid, int gridSize, int* gridColSize) {
    int m = gridSize;
    int n = gridColSize[0];

    // a valid parentheses string must have even length
    if ((m + n - 1) % 2 == 1) return false;

    // first and last characters must be '(' and ')'
    if (grid[0][0] != '(' || grid[m - 1][n - 1] != ')') return false;

    int maxBalance = (m + n - 1) / 2;
    int balanceSize = maxBalance + 1;

    // Flattening the 3D grid [m][n][balanceSize] into a flat 1D block to maximize hardware speed
    bool* dp = (bool*)calloc(m * n * balanceSize, sizeof(bool));

    // dp[0][0][1] = true
    dp[1] = true;

    for (int r = 0; r < m; r++) {
        for (int c = 0; c < n; c++) {
            if (r == 0 && c == 0) continue;

            int change = (grid[r][c] == '(') ? 1 : -1;
            int currentCellOffset = (r * n + c) * balanceSize;

            for (int balance = 0; balance <= maxBalance; balance++) {
                int previousBalance = balance - change;

                if (previousBalance < 0 || previousBalance > maxBalance) continue;

                bool possible = false;

                if (r > 0) {
                    int topCellOffset = ((r - 1) * n + c) * balanceSize;
                    possible |= dp[topCellOffset + previousBalance];
                }

                if (c > 0) {
                    int leftCellOffset = (r * n + (c - 1)) * balanceSize;
                    possible |= dp[leftCellOffset + previousBalance];
                }

                dp[currentCellOffset + balance] = possible;
            }
        }
    }

    int finalCellOffset = ((m - 1) * n + (n - 1)) * balanceSize;
    bool result = dp[finalCellOffset + 0];

    free(dp);
    return result;
}
