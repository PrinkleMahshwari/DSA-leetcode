class Solution:
    def checkDivisibility(self, n: int) -> bool:
        original = n
        sum_val = 0  # Renamed from 'sum' to avoid shadowing Python's built-in sum function
        product = 1

        while n > 0:
            digit = n % 10

            sum_val += digit
            product *= digit

            n //= 10  # Enforce floor/integer division

        if sum_val + product == 0:
            return False

        return original % (sum_val + product) == 0
