class Solution:
    def smallestNumber(self, n: int, t: int) -> int:
        for num in range(n, n + 11):
            product = 1
            temp = num
            
            # For 0, the product of digits is 0
            if temp == 0:
                product = 0
                
            while temp > 0:
                product *= temp % 10
                temp //= 10
                
            if product % t == 0:
                return num
                
        return -1
