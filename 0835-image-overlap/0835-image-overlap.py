class Solution:
    def largestOverlap(self, img1: list[list[int]], img2: list[list[int]]) -> int:
        n = len(img1)
        size = 2 * n - 1

        # Correct initialization for 2D list in Python
        count = [[0] * size for _ in range(size)]
        answer = 0

        for r1 in range(n):
            for c1 in range(n):
                if img1[r1][c1] == 0:
                    continue

                for r2 in range(n):
                    for c2 in range(n):
                        if img2[r2][c2] == 0:
                            continue

                        dr = r2 - r1 + n - 1
                        dc = c2 - c1 + n - 1

                        count[dr][dc] += 1
                        if count[dr][dc] > answer:
                            answer = count[dr][dc]

        return answer
