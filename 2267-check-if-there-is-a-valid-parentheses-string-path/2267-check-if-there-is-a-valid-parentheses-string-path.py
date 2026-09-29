class Solution:
    def hasValidPath(self, grid: list[list[str]]) -> bool:
        m = len(grid)
        n = len(grid[0])

        # a valid parentheses string must have even length
        if (m + n - 1) % 2 == 1:
            return False

        # first and last characters must be '(' and ')'
        if grid[0][0] != '(' or grid[m - 1][n - 1] != ')':
            return False

        maxBalance = (m + n - 1) // 2

        # 3D list for DP state caching
        dp = [[[False] * (maxBalance + 1) for _ in range(n)] for _ in range(m)]

        dp[0][0][1] = True

        for r in range(m):
            for c in range(n):
                if r == 0 and c == 0:
                    continue

                change = 1 if grid[r][c] == '(' else -1

                for balance in range(maxBalance + 1):
                    previousBalance = balance - change

                    if previousBalance < 0 or previousBalance > maxBalance:
                        continue

                    possible = False

                    if r > 0:
                        possible |= dp[r - 1][c][previousBalance]

                    if c > 0:
                        possible |= dp[r][c - 1][previousBalance]

                    dp[r][c][balance] = possible

        return dp[m - 1][n - 1][0]
