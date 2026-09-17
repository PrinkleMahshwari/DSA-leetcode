class Solution:
    def minSumOfLengths(self, arr: list[int], target: int) -> int:
        n = len(arr)
        INF = n + 1

        # Correct bracket list initialization to avoid syntax bugs
        best = [INF] * (n + 1)

        left = 0
        sum_val = 0
        shortest = INF
        answer = INF

        for right in range(n):
            sum_val += arr[right]

            while sum_val > target:
                sum_val -= arr[left]
                left += 1

            if sum_val == target:
                length = right - left + 1

                if best[left] != INF:
                    answer = min(answer, best[left] + length)
                shortest = min(shortest, length)
                
            best[right + 1] = shortest

        return -1 if answer == INF else answer
