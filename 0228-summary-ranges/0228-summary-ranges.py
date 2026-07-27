class Solution:
    def summaryRanges(self, nums: list[int]) -> list[str]:
        result = []
        i = 0
        n = len(nums)

        while i < n:
            start = nums[i]

            # Shift the inner boundary index step forward
            while i + 1 < n and nums[i + 1] == nums[i] + 1:
                i += 1

            # Format strings using efficient Python f-strings
            if start == nums[i]:
                result.append(str(start))
            else:
                result.append(f"{start}->{nums[i]}")

            i += 1

        return result
