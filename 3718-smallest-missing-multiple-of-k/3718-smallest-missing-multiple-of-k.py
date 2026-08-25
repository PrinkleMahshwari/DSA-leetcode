class Solution:
    def missingMultiple(self, nums: list[int], k: int) -> int:
        # Use a hash set for O(1) membership lookups
        num_set = set(nums)

        multiple = k

        # Increment by k until we find a value missing from the set
        while multiple in num_set:
            multiple += k

        return multiple
