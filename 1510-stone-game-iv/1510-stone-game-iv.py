class Solution:
    def winnerSquareGame(self, n: int) -> bool:
        # dp[i] represents if the current player can force a win with 'i' stones
        dp = [False] * (n + 1)
        
        dp[0] = False
        
        for i in range(1, n + 1):
            j = 1
            while j * j <= i:
                square = j * j
                
                # If the remaining stones put the opponent in a losing state, we win
                if not dp[i - square]:
                    dp[i] = True
                    break
                j += 1
                
        return dp[n]
