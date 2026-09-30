class Solution:
    def maxDepthAfterSplit(self, seq: str) -> list[int]:
        n = len(seq)
        answer = [0] * n

        depth = 0

        for i in range(n):
            if seq[i] == '(':
                depth += 1
                answer[i] = depth & 1
            else:
                answer[i] = depth & 1
                depth -= 1

        return answer
