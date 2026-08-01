class Solution:
    def predictTheWinner(self, nums: list[int]) -> bool:
        n = len(nums)
        # Initialize a 2D matrix filled with 0s
        dp = [[0] * n for _ in range(n)]

        # Base case: single element subarray, current player takes it
        for i in range(n):
            dp[i][i] = nums[i]

        # Filling dp table for increasing subarray lengths
        for length in range(2, n + 1):
            for i in range(n - length + 1):
                j = i + length - 1
                # Choice - opponent's relative advantage on remaining subarray
                dp[i][j] = max(
                    nums[i] - dp[i + 1][j],  # Picking left end
                    nums[j] - dp[i][j - 1]   # Picking right end
                )

        # Player 1 wins if the total score difference is >= 0
        return dp[0][n - 1] >= 0
