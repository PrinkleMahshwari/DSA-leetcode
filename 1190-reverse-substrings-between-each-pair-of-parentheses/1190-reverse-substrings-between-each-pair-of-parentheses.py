class Solution:
    def reverseParentheses(self, s: str) -> str:
        n = len(s)
        pair = [0] * n
        stack = []

        # Find matching parentheses
        for i in range(n):
            if s[i] == '(':
                stack.append(i)
            elif s[i] == ')':
                open_idx = stack.pop()
                pair[open_idx] = i
                pair[i] = open_idx

        result = []
        i = 0
        direction = 1

        while 0 <= i < n:
            current = s[i]

            if current == '(' or current == ')':
                # Jump to the matching parenthesis
                i = pair[i]
                # Reverse traversal direction
                direction = -direction
            else:
                result.append(current)

            i += direction

        return "".join(result)
