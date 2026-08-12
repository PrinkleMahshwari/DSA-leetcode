class Solution:
    def maxSubarrayLength(self, nums: list[int], k: int) -> int:
        # Dictionary to store the frequencies of elements
        freq = {}

        left = 0
        ans = 0

        for right in range(len(nums)):
            r_num = nums[right]
            freq[r_num] = freq.get(r_num, 0) + 1

            # Shrink the window from the left if the current element's frequency exceeds k
            while freq[r_num] > k:
                l_num = nums[left]
                freq[l_num] -= 1
                left += 1

            ans = max(ans, right - left + 1)

        return ans
