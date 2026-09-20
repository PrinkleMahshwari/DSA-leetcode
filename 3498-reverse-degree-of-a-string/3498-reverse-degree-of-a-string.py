class Solution:
    def reverseDegree(self, s: str) -> int:
        result = 0

        for i, char in enumerate(s):
            reverse_value = 26 - (ord(char) - ord('a'))
            result += reverse_value * (i + 1)

        return result
