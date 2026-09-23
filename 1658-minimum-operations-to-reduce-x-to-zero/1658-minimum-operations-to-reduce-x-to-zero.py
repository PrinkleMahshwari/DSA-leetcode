class Solution:
    def minOperations(self, nums: list[int], x: int) -> int:
        n = len(nums)

        total = sum(nums)
        target = total - x

        if target < 0: return -1
        if target == 0: return n

        left = 0
        current_sum = 0
        maxLength = -1

        for right in range(n):
            current_sum += nums[right]

            while left <= right and current_sum > target:
                current_sum -= nums[left]
                left += 1

            if current_sum == target:
                maxLength = max(maxLength, right - left + 1)

        return -1 if maxLength == -1 else n - maxLength
