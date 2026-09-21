class Solution:
    def resultArray(self, nums: list[int], k: int) -> list[int]:
        answer = [0] * k
        dp = [0] * k

        for num in nums:
            value = num % k
            next_dp = [0] * k

            # start a new subarray with nums[i]
            next_dp[value] += 1

            # extend every subarray ending at the previous index
            for r in range(k):
                if dp[r] > 0:
                    new_remainder = (r * value) % k
                    next_dp[new_remainder] += dp[r]

            # add all subarrays ending at this index
            for r in range(k):
                answer[r] += next_dp[r]
                
            dp = next_dp

        return answer
