class Solution:
    def sortableIntegers(self, nums: List[int]) -> int:
        n = len(nums)
        sorted_nums = sorted(nums)
        answer = 0

        def is_rotation(start: int, k: int) -> bool:
            # Build the KMP prefix table for the sorted block
            lps = [0] * k
            j = 0

            for i in range(1, k):
                while j > 0 and sorted_nums[start + i] != sorted_nums[start + j]:
                    j = lps[j - 1]
                if sorted_nums[start + i] == sorted_nums[start + j]:
                    j += 1
                lps[i] = j

            # Search the sorted block in two cyclic copies of nums block
            j = 0
            for i in range(2 * k - 1):
                value = nums[start + (i % k)]

                while j > 0 and value != sorted_nums[start + j]:
                    j = lps[j - 1]
                if value == sorted_nums[start + j]:
                    j += 1
                if j == k:
                    return True

            return False

        # Only divisors of n can be sortable
        for k in range(1, n + 1):
            if n % k != 0:
                continue

            sortable = True

            # Check every block of length k
            for start in range(0, n, k):
                if not is_rotation(start, k):
                    sortable = False
                    break

            if sortable:
                answer += k

        return answer
