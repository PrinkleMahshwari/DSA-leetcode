class Solution:
    def largestInteger(self, nums: list[int], k: int) -> int:
        n = len(nums)

        # case 1: only one subarray exists (k == n)
        if k == n:
            max_val = -1
            for num in nums:
                max_val = max(max_val, num)
            return max_val

        freq = [0] * 51
        for num in nums:
            freq[num] += 1

        # case 2: every subarray has size 1
        if k == 1:
            for x in range(50, -1, -1):
                if freq[x] == 1:
                    return x
            return -1

        # case 3: 1 < k < n
        ans = -1
        if freq[nums[0]] == 1:
            ans = max(ans, nums[0])
            
        if freq[nums[n - 1]] == 1:
            ans = max(ans, nums[n - 1])

        return ans
