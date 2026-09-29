/**
 * @param {character[][]} grid
 * @return {boolean}
 */
var hasValidPath = function(grid) {
    const m = grid.length;
    const n = grid[0].length;

    // a valid parentheses string must have even length
    if ((m + n - 1) % 2 === 1) return false;

    // first and last characters must be '(' and ')'
    if (grid[0][0] !== '(' || grid[m - 1][n - 1] !== ')') return false;

    const maxBalance = Math.floor((m + n - 1) / 2);

    // 3D DP Array representation
    const dp = Array.from({ length: m }, () =>
        Array.from({ length: n }, () => new Uint8Array(maxBalance + 1))
    );

    dp[0][0][1] = 1;

    for (let r = 0; r < m; r++) {
        for (let c = 0; c < n; c++) {
            if (r === 0 && c === 0) continue;

            const change = grid[r][c] === '(' ? 1 : -1;

            for (let balance = 0; balance <= maxBalance; balance++) {
                const previousBalance = balance - change;

                if (previousBalance < 0 || previousBalance > maxBalance) continue;

                let possible = false;

                if (r > 0) possible ||= dp[r - 1][c][previousBalance];
                if (c > 0) possible ||= dp[r][c - 1][previousBalance];

                dp[r][c][balance] = possible ? 1 : 0;
            }
        }
    }

    return dp[m - 1][n - 1][0] === 1;
};
