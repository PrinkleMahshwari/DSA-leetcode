class Solution:
    def missingInteger(self, nums: list[int]) -> int:
        prefix_sum = nums[0]
        
        # Step 1: Calculate the sum of the longest sequential prefix
        for i in range(1, len(nums)):
            if nums[i] == nums[i - 1] + 1:
                prefix_sum += nums[i]
            else:
                break  # Stop as soon as the sequential property breaks
                
        # Step 2: Use a hash set for O(1) membership lookups
        num_set = set(nums)
        
        # Step 3: Increment prefix_sum until we find a value missing from the set
        while prefix_sum in num_set:
            prefix_sum += 1
            
        return prefix_sum
