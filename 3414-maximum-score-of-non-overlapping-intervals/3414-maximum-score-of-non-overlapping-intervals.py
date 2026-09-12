class Solution:
    def maximumWeight(self, intervals: list[list[int]]) -> list[int]:
        n = len(intervals)

        # [left, right, weight, originalIndex]
        a = []
        for i in range(n):
            a.append([intervals[i][0], intervals[i][1], intervals[i][2], i])

        # Sort by right endpoint
        a.sort(key=lambda x: x[1])

        # prev[i] = last interval ending before a[i] starts
        prev = [-1] * n
        for i in range(n):
            lo = 0
            hi = i - 1
            pos = -1

            while lo <= hi:
                mid = lo + (hi - lo) // 2
                if a[mid][1] < a[i][0]:
                    pos = mid
                    lo = mid + 1
                else:
                    hi = mid - 1
            prev[i] = pos

        # State object helper
        # format: (score, list_of_ids)
        dp = [[(0, []) for _ in range(n + 1)] for _ in range(5)]

        def add(ids, value):
            result = []
            i = 0
            n_ids = len(ids)
            while i < n_ids and ids[i] < value:
                result.append(ids[i])
                i += 1
            result.append(value)
            while i < n_ids:
                result.append(ids[i])
                i += 1
            return result

        def better(state_a, state_b):
            if state_a[0] != state_b[0]:
                return state_a if state_a[0] > state_b[0] else state_b
            # In Python, lists compare elements lexicographically out-of-the-box
            return state_a if state_a[1] <= state_b[1] else state_b

        for k in range(1, 5):
            for i in range(1, n + 1):
                cur = i - 1
                skip = dp[k][i - 1]
                base = dp[k - 1][prev[cur] + 1]

                new_ids = add(base[1], a[cur][3])
                take = (base[0] + a[cur][2], new_ids)

                dp[k][i] = better(skip, take)

        return dp[4][n][1]
