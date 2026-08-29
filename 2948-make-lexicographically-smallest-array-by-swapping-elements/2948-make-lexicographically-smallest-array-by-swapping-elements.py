class Solution:
    def lexicographicallySmallestArray(self, nums: list[int], limit: int) -> list[int]:
        n = len(nums)

        # Store (value, original_index) tracking pairs
        arr = []
        for i in range(n):
            arr.append((nums[i], i))

        # Sort by value ascending
        arr.sort(key=lambda x: x[0])

        result = [0] * n
        start = 0

        while start < n:
            end = start

            # Find one connected component within difference limits
            while end + 1 < n and arr[end + 1][0] - arr[end][0] <= limit:
                end += 1

            size = end - start + 1
            indices = []

            # Collect original indices
            for i in range(size):
                indices.append(arr[start + i][1])

            # Original position index boundaries must be processed in ascending order
            indices.sort()

            # Values are already sorted in ascending order inside 'arr'
            for i in range(size):
                result[indices[i]] = arr[start + i][0]

            start = end + 1

        return result
