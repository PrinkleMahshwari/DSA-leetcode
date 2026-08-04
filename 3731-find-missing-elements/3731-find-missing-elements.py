class Solution:
    def findMissingElements(self, nums: list[int]) -> list[int]:
        if not nums:
            return []
            
        # Finding min and max in one pass
        minimum = nums[0]
        maximum = nums[0]
        for num in nums[1:]:
            if num < minimum: minimum = num
            if num > maximum: maximum = num
            
        # Using a flat list for O(1) direct offset index tracking
        range_len = maximum - minimum + 1
        present = [False] * range_len
        for num in nums:
            present[num - minimum] = True
            
        # Collecting missing elements in sorted order
        result = []
        for i in range(1, range_len - 1):
            if not present[i]:
                result.append(i + minimum)
                
        return result
