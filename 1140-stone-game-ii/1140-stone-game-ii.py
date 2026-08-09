class Solution:
    def stoneGameII(self, piles: list[int]) -> int:
        n = len(piles)
        
        # Initialize memoization table with 0s
        dp = [[0] * (n + 1) for _ in range(n)]
        
        # Build the suffix sum array from right to left
        suffix = [0] * (n + 1)
        for i in range(n - 1, -1, -1):
            suffix[i] = suffix[i + 1] + piles[i]
            
        def solve(i: int, m: int) -> int:
            # Base case: No piles left
            if i >= n:
                return 0
            
            # If the current player can take all remaining piles
            if 2 * m >= n - i:
                return suffix[i]
            
            # Return cached result if available
            if dp[i][m] != 0:
                return dp[i][m]
                
            best = 0
            # Explore all possible moves for taking 'x' piles
            for x in range(1, 2 * m + 1):
                next_m = max(m, x)
                opponent = solve(i + x, next_m)
                current = suffix[i] - opponent
                best = max(best, current)
                
            dp[i][m] = best
            return best

        return solve(0, 1)
