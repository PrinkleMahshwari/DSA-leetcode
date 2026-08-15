class Solution:
    def longestSubsequence(self, nums: list[int]) -> int:
        xor = 0
        hasNonZero = False

        for num in nums:
            xor ^= num

            if num != 0:
                hasNonZero = True

        if xor != 0:
            return len(nums)

        return len(nums) - 1 if hasNonZero else 0
