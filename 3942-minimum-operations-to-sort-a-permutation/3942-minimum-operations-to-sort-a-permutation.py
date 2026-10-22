class Solution:
    def minOperations(self, nums: list[int]) -> int:
        n = len(nums)

        if n == 1:
            return 0
        
        index_zero = 0
        drops = 0
        rises = 0

        for i in range(n):
            if nums[i] == 0:
                index_zero = i

            next_val = nums[(i + 1) % n]

            if nums[i] > next_val:
                drops += 1

            if nums[i] < next_val:
                rises += 1

        if drops == 1 and index_zero == 0:
            return 0

        answer = float('inf')

        # Case 1: nums is a cyclic rotation of increasing order
        if drops == 1:
            rotate_left = index_zero
            reverse_rotate_reverse = 2 + (n - index_zero) % n

            answer = min(answer, rotate_left, reverse_rotate_reverse)

        # Case 2: nums is a cyclic rotation of decreasing order
        if rises == 1:
            reverse_index_zero = n - 1 - index_zero

            reverse_then_rotate = 1 + reverse_index_zero
            rotate_then_reverse = 1 + (n - reverse_index_zero) % n

            answer = min(answer, reverse_then_rotate, rotate_then_reverse)

        return -1 if answer == float('inf') else answer
