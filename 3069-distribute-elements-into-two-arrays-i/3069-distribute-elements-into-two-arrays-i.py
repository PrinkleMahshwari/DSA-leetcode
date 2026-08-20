class Solution:
    def resultArray(self, nums: list[int]) -> list[int]:
        arr1 = []
        arr2 = []

        # first two operations are fixed
        arr1.append(nums[0])
        arr2.append(nums[1])

        last1 = nums[0]
        last2 = nums[1]

        # process remaining elements
        for i in range(2, len(nums)):
            if last1 > last2:
                arr1.append(nums[i])
                last1 = nums[i]
            else:
                arr2.append(nums[i])
                last2 = nums[i]

        # concatenate arr1 and arr2
        return arr1 + arr2
