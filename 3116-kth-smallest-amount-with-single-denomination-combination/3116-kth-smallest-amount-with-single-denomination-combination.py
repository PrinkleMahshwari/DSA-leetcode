class Solution:
    def findKthSmallest(self, coins: list[int], k: int) -> int:
        minCoin = min(coins)
        
        left = 1
        right = minCoin * k

        def gcd(a: int, b: int) -> int:
            while b != 0:
                a, b = b, a % b
            return a

        def lcm(a: int, b: int) -> int:
            return (a // gcd(a, b)) * b

        def count(x: int, index: int, current_lcm: int, selected: int) -> int:
            result = 0
            for i in range(index, len(coins)):
                new_lcm = lcm(current_lcm, coins[i])
                
                if new_lcm > x:
                    continue
                
                contribution = x // new_lcm
                
                if (selected + 1) % 2 == 1:
                    result += contribution
                else:
                    result -= contribution
                    
                result += count(x, i + 1, new_lcm, selected + 1)
                
            return result

        while left < right:
            mid = left + (right - left) // 2
            
            if count(mid, 0, 1, 0) >= k:
                right = mid
            else:
                left = mid + 1
                
        return left
