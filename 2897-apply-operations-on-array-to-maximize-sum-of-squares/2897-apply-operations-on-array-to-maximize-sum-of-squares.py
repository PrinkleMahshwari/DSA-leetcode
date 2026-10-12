class Solution:
    def maxSum(self, nums: List[int], k: int) -> int:
        bits = [0] * 31
        MOD = 1_000_000_007

        # Count how many numbers have each bit set
        for num in nums:
            for b in range(31):
                if (num & (1 << b)) != 0:
                    bits[b] += 1

        result = 0

        # Greedily build the largest k numbers
        for _ in range(k):
            num = 0

            for b in range(31):
                if bits[b] > 0:
                    num |= (1 << b)
                    bits[b] -= 1

            result = (result + num * num) % MOD

        return result
