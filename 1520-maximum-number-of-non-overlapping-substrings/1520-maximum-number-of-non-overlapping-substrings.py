class Solution:
    def maxNumOfSubstrings(self, s: str) -> list[str]:
        n = len(s)

        first = [n] * 26
        last = [-1] * 26

        # Find first and last occurrence of every character
        for i in range(n):
            c = ord(s[i]) - ord('a')
            if first[c] == n:
                first[c] = i
            last[c] = i

        intervals = []

        # Build every valid minimal interval
        for c in range(26):
            if last[c] == -1:
                continue

            left = first[c]
            right = last[c]
            valid = True

            i = left
            while i <= right:
                current = ord(s[i]) - ord('a')

                # This character appeared before our left boundary.
                if first[current] < left:
                    valid = False
                    break

                # Need to include all occurrences of this character.
                right = max(right, last[current])
                i += 1

            if valid:
                intervals.append([left, right])

        # Sort by ending position
        intervals.sort(key=lambda x: x[1])

        answer = []
        end = -1

        for left, right in intervals:
            if left > end:
                answer.append(s[left:right + 1])
                end = right

        return answer
