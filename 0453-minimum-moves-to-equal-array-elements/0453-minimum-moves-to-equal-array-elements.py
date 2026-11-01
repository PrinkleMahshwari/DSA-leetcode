class Solution:
    def minMoves(self, nums: list[int]) -> int:
        # Python's built-in min and sum are optimized in C
        min_val = min(nums)
        sum_val = sum(nums)
        
        return sum_val - len(nums) * min_val
