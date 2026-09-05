class Solution:
    def firstStableIndex(self, nums: list[int], k: int) -> int:
        n = len(nums)
        if n == 0:
            return -1

        prefix_max = [0] * n
        suff_min = [0] * n

        # Compute prefix maximums
        prefix_max[0] = nums[0]
        for i in range(1, n):
            prefix_max[i] = max(prefix_max[i - 1], nums[i])

        # Compute suffix minimums
        suff_min[n - 1] = nums[n - 1]
        for i in range(n - 2, -1, -1):
            suff_min[i] = min(suff_min[i + 1], nums[i])

        # Find smallest stable index
        for i in range(n):
            if prefix_max[i] - suff_min[i] <= k:
                return i

        return -1
