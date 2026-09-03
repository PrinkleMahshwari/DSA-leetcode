class Solution:
    def uniformArray(self, nums1: list[int]) -> bool:
        smallestOdd = float('inf')
        smallestEven = float('inf')

        for num in nums1:
            if (num & 1) == 0:
                smallestEven = min(smallestEven, num)
            else:
                smallestOdd = min(smallestOdd, num)

        # all elements already have the same parity
        if smallestOdd == float('inf') or smallestEven == float('inf'):
            return True
        
        # mixed parity: make everything odd
        return smallestOdd < smallestEven
