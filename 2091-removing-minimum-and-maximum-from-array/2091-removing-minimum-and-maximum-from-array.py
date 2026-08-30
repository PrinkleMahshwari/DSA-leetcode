class Solution:
    def minimumDeletions(self, nums: list[int]) -> int:
        n = len(nums)

        minIndex = 0
        maxIndex = 0

        # find min and max indices 
        for i in range(n):
            if nums[i] < nums[minIndex]:
                minIndex = i
            
            if nums[i] > nums[maxIndex]:
                maxIndex = i

        left = min(minIndex, maxIndex)
        right = max(minIndex, maxIndex)

        # remove both from the front
        fromFront = right + 1

        # remove both from the back
        fromBack = n - left

        # remove one from front, and one from back
        fromBoth = (left + 1) + (n - right)

        return min(fromFront, min(fromBack, fromBoth))
