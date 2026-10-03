class Solution:
    def longestValidParentheses(self, s: str) -> int:
        maxLength = 0
        stack = [0] * (len(s) + 1)
        top = 0

        # boundary before the string starts
        stack[top] = -1

        for i in range(len(s)):
            if s[i] == '(':
                top += 1
                stack[top] = i
            else:
                top -= 1

                # unmatched ')'
                if top < 0:
                    top = 0
                    stack[top] = i
                else:
                    maxLength = max(maxLength, i - stack[top])

        return maxLength
