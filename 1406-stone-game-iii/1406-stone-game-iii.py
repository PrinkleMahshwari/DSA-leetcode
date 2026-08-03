class Solution:
    def stoneGameIII(self, stoneValue: list[int]) -> str:
        n = len(stoneValue)
        # Allocate flat list matching the size layout
        dp = [0] * (n + 1)

        for i in range(n - 1, -1, -1):
            dp[i] = float('-inf') # Handles negative infinity boundaries cleanly
            take = 0
            
            for k in range(3):
                if i + k < n:
                    take += stoneValue[i + k]
                    dp[i] = max(dp[i], take - dp[i + k + 1])
                    
        if dp[0] > 0: return "Alice"
        if dp[0] < 0: return "Bob"
        return "Tie"
