class Solution:
    def minimumCost(self, nums: list[int]) -> int:
        # Step 1: Sort the array to find the true median
        nums.sort()
        n = len(nums)
        median = nums[n // 2]

        # Step 2: Get the closest valid palindrome numbers surrounding the median
        lower_palindrome = self.getLowerPalindrome(median)
        upper_palindrome = self.getUpperPalindrome(median)

        # Step 3: Compute total operational cost for both candidates
        cost_with_lower = self.calculateCost(nums, lower_palindrome)
        cost_with_upper = self.calculateCost(nums, upper_palindrome)

        # Step 4: Return the minimal absolute operation cost
        return min(cost_with_lower, cost_with_upper)

    def getLowerPalindrome(self, x: int) -> int:
        while not self.isPalindrome(x):
            x -= 1
        return x

    def getUpperPalindrome(self, x: int) -> int:
        while not self.isPalindrome(x):
            x += 1
        return x

    def isPalindrome(self, x: int) -> bool:
        s = str(x)
        return s == s[::-1]

    def calculateCost(self, nums: list[int], target: int) -> int:
        cost = 0
        for num in nums:
            cost += abs(num - target)
        return cost
