class Solution:
    def stoneGameIX(self, stones: list[int]) -> bool:
        count = [0] * 3

        for stone in stones:
            count[stone % 3] += 1
        
        # if there are no stones with remainder 1 or 2,
        # Alice can't avoid eventually losing
        if count[1] == 0 and count[2] == 0:
            return False
        
        # if count[0] is even, the winner depends on 
        # whether both remainder groups exist
        if count[0] % 2 == 0:
            return count[1] > 0 and count[2] > 0
        
        # count[0] is odd
        # Alice wins if one of the two remainder groups
        # has at least 2 more stones than other 
        return abs(count[1] - count[2]) > 2
