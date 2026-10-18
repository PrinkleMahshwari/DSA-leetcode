class Solution:
    def numOfWays(self, nums: List[int]) -> int:
        MOD = 1_000_000_007
        n = len(nums)

        # Precompute combinations using Pascal's triangle
        comb = [[0]* (n + 1) for _ in range(n + 1)]
        
        for i in range(n + 1):
            comb[i][0] = 1
            comb[i][i] = 1
            for j in range(1, i):
                comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD

        def solve(lst: List[int]) -> int:
            length = len(lst)
            if length <= 2:
                return 1

            root = lst[0]
            left = [x for x in lst[1:] if x < root]
            right = [x for x in lst[1:] if x > root]

            ways = comb[length - 1][len(left)]
            ways = (ways * solve(left)) % MOD
            ways = (ways * solve(right)) % MOD

            return ways

        # Subtract the original ordering
        return (solve(nums) - 1 + MOD) % MOD
