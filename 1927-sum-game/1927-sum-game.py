class Solution:
    def sumGame(self, num: str) -> bool:
        n = len(num)
        half = n // 2

        leftSum = 0
        rightSum = 0

        qLeft = 0
        qRight = 0

        for i in range(half):
            c = num[i]

            if c == '?':
                qLeft += 1
            else:
                leftSum += ord(c) - ord('0')

        for i in range(half, n):
            c = num[i]

            if c == '?':
                qRight += 1
            else:
                rightSum += ord(c) - ord('0')

        diff = leftSum - rightSum

        return 2 * diff != 9 * (qRight - qLeft)
