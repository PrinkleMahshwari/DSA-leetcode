class Solution:
    def isHappy(self, n: int) -> bool:
        seen = set()

        while n != 1 and n not in seen:
            seen.add(n)
            n = self.next(n)

        return n == 1

    def next(self, n: int) -> int:
        sum_val = 0

        while n > 0:
            digit = n % 10
            sum_val += digit * digit
            n //= 10 # Force integer floor division

        return sum_val
