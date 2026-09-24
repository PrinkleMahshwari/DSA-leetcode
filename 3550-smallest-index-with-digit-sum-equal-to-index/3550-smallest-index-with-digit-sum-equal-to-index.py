class Solution:
    def smallestIndex(self, nums: list[int]) -> int:
        for i in range(len(nums)):
            num = nums[i]
            sum_val = 0 # Renamed 'sum' to avoid shadowing Python's built-in function

            while num > 0:
                sum_val += num % 10
                num //= 10 # Enforce integer/floor division

            if sum_val == i: return i

        return -1
