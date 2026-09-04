class Solution:
    def firstStableIndex(self, nums: list[int], k: int) -> int:
        n = len(nums)
        if n == 0:
            return -1
            
        # Step 1: Precompute the minimum value for every suffix
        suffix_min = [0] * n
        suffix_min[-1] = nums[-1]
        for i in range(n - 2, -1, -1):
            suffix_min[i] = min(nums[i], suffix_min[i + 1])
            
        # Step 2: Traverse from left to right
        max_left = float('-inf')
        for i in range(n):
            max_left = max(max_left, nums[i])
            if max_left - suffix_min[i] <= k:
                return i
                
        return -1
