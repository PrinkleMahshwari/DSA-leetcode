class Solution:
    def numberOfSets(self, n: int, k: int) -> int:
        MOD = 1_000_000_007

        dp = [1] * n

        for j in range(1, k + 1):
            next_dp = [0] * n
            current_sum = 0

            for i in range(1, n):
                current_sum = (current_sum + dp[i - 1]) % MOD
                next_dp[i] = (next_dp[i - 1] + current_sum) % MOD
            dp = next_dp

        return dp[n - 1]
