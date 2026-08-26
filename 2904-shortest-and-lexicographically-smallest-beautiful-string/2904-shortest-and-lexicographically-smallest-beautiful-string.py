class Solution:
    def shortestBeautifulSubstring(self, s: str, k: int) -> str:
        ones = []

        # store positions of all 1s
        for i in range(len(s)):
            if s[i] == '1':
                ones.append(i)

        # Not enough 1s
        if len(ones) < k:
            return ""

        answer = ""

        # try every possible starting 1
        for i in range(len(ones) - k + 1):
            start = ones[i]
            end = ones[i + k - 1]

            candidate = s[start:end + 1]

            if (answer == "" 
                    or len(candidate) < len(answer) 
                    or (len(candidate) == len(answer) and candidate < answer)):
                answer = candidate

        return answer
