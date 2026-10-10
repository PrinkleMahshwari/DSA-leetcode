class Solution:
    def minSumSquareDiff(self, nums1: list[int], nums2: list[int], k1: int, k2: int) -> int:
        n = len(nums1)
        diff = [0] * n

        maxDiff = 0
        totalDiff = 0

        for i in range(n):
            diff[i] = abs(nums1[i] - nums2[i])
            if diff[i] > maxDiff:
                maxDiff = diff[i]
            totalDiff += diff[i]

        k = k1 + k2

        # If all differences can become zero
        if k >= totalDiff:
            return 0

        # Binary search for the smallest possible max difference
        low = 0
        high = maxDiff

        while low < high:
            mid = low + (high - low) // 2
            needed = 0

            for d in diff:
                if d > mid:
                    needed += d - mid

            if needed <= k:
                high = mid
            else:
                low = mid + 1

        limit = low
        needed = 0
        result = 0

        for d in diff:
            reduced = min(d, limit)
            needed += d - reduced
            result += reduced * reduced

        # Use remaining operations to reduce some differences by one more
        remaining = k - needed
        result -= remaining * (2 * limit - 1)

        return result
