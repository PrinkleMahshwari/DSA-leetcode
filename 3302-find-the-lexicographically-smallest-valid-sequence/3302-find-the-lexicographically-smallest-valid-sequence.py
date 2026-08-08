class Solution:
    def validSequence(self, word1: str, word2: str) -> list[int]:
        n = len(word1)
        m = len(word2)

        # Space Optimized: Allocated exactly bound to the length of word2
        lastValidW1Idx = [-1] * m

        j = m - 1
        for i in range(n - 1, -1, -1):
            # Direct string indices lookups bypass list allocation delays
            if j >= 0 and word1[i] == word2[j]:
                lastValidW1Idx[j] = i
                j -= 1

        answer = [0] * m
        w2Idx = 0
        changed = False

        for i in range(n):
            if w2Idx >= m:
                break
                
            c1 = word1[i]
            c2 = word2[w2Idx]

            # Case 1: Fast exact matching track
            if c1 == c2:
                answer[w2Idx] = i
                w2Idx += 1
            # Case 2: Skip/Mismatch configuration slot evaluation
            elif not changed and (w2Idx == m - 1 or (lastValidW1Idx[w2Idx + 1] != -1 and lastValidW1Idx[w2Idx + 1] > i)):
                answer[w2Idx] = i
                w2Idx += 1
                changed = True

        if w2Idx < m:
            return []

        return answer
