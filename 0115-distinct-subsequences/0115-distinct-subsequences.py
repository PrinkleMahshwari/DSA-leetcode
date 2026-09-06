class Solution:
    def numDistinct(self, s: str, t: str) -> int:
        n = len(s)
        m = len(t)

        # Correct initialization for Python lists
        dp = [0] * (m + 1)
        dp[0] = 1

        for i in range(n):
            for j in range(m, 0, -1):
                if s[i] == t[j - 1]:
                    dp[j] += dp[j - 1]

        return dp[m]
