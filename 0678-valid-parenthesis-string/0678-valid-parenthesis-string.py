class Solution:
    def checkValidString(self, s: str) -> bool:
        low = 0
        high = 0

        for ch in s:
            if ch == '(':
                low += 1
                high += 1
            elif ch == ')':
                low -= 1
                high -= 1
            else:
                # '*'
                low -= 1
                high += 1

            # minimum balance cannot be negative
            low = max(0, low)

            # even the maximum balance is negative
            if high < 0: 
                return False

        return low == 0
