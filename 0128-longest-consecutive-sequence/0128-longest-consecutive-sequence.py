class Solution:
    def longestConsecutive(self, nums: list[int]) -> int:
        if len(nums) == 0:
            return 0

        # Store all numbers (automatically deduplicates)
        num_set = set(nums)
        longest = 0

        for num in num_set:
            # Check if this number is the start of a sequence
            if (num - 1) not in num_set:
                current = num
                length = 1

                # Count consecutive numbers
                while (current + 1) in num_set:
                    current += 1
                    length += 1

                longest = max(longest, length)

        return longest
