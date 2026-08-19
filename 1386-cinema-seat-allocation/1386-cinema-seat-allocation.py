class Solution:
    def maxNumberOfFamilies(self, n: int, reservedSeats: list[list[int]]) -> int:
        # Dictionary to store reservation bitmask for each affected row
        rows = {}

        for row, col in reservedSeats:
            rows[row] = rows.get(row, 0) | (1 << col)

        # All rows without reservations can fit 2 groups
        answer = (n - len(rows)) * 2

        # Mask for possible seating combinations
        left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5)
        middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7)
        right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9)

        for mask in rows.values():
            canLeft = (mask & left) == 0
            canMiddle = (mask & middle) == 0
            canRight = (mask & right) == 0

            if canLeft and canRight:
                answer += 2
            elif canLeft or canMiddle or canRight:
                answer += 1

        return answer
