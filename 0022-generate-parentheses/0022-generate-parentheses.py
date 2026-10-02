class Solution:
    def generateParenthesis(self, n: int) -> list[str]:
        result = []
        current = []

        def backtrack(open_count, close_count):
            # complete valid combination
            if len(current) == 2 * n:
                result.append("".join(current))
                return

            # add opening parenthesis
            if open_count < n:
                current.append('(')
                backtrack(open_count + 1, close_count)
                current.pop()

            # add closing parenthesis only when valid
            if close_count < open_count:
                current.append(')')
                backtrack(open_count, close_count + 1)
                current.pop()

        backtrack(0, 0)
        return result
