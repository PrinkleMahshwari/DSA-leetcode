# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def averageOfSubtree(self, root: Optional[TreeNode]) -> int:
        self.answer = 0

        def dfs(node):
            # Base case: returns a list with [sum, count]
            if not node:
                return [0, 0]

            left = dfs(node.left)
            right = dfs(node.right)

            total_sum = node.val + left[0] + right[0]
            count = 1 + left[1] + right[1]

            # Floor division '//' matches JavaScript's Math.floor(sum / count)
            if total_sum // count == node.val:
                self.answer += 1

            return [total_sum, count]

        dfs(root)
        return self.answer
