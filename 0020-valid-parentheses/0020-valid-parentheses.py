class Solution:
    def isValid(self, s: str) -> bool:
        # Array-allocated list to replicate the fixed-size stack
        stack = [''] * len(s)
        top = -1

        for ch in s:
            # opening brackets
            if ch == '(' or ch == '[' or ch == '{':
                top += 1
                stack[top] = ch
            # closing bracket
            else:
                if top == -1: 
                    return False

                open_ch = stack[top]

                if (ch == ')' and open_ch != '(') or \
                   (ch == ']' and open_ch != '[') or \
                   (ch == '}' and open_ch != '{'): 
                    return False

                top -= 1

        return top == -1
