class Solution:
    def maxDepth(self, s: str) -> int:
        depth = 0
        max_depth = 0 # Renamed variable slightly to comply with Python snake_case conventions

        for ch in s:
            if ch == '(':
                depth += 1
                max_depth = max(max_depth, depth)
            elif ch == ')':
                depth -= 1

        return max_depth
