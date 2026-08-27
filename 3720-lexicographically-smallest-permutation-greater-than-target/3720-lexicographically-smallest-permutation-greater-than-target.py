class Solution:
    def lexGreaterPermutation(self, s: str, target: str) -> str:
        n = len(s)
        freq = [0] * 26

        for c in s:
            freq[ord(c) - ord('a')] += 1
        
        ans = [''] * n
        i = 0

        # match target as long as possible
        while i < n:
            idx = ord(target[i]) - ord('a')

            if freq[idx] > 0:
                ans[i] = target[i]
                freq[idx] -= 1
                i += 1
            else:
                break

        def findGreater(freq_arr, current):
            for c in range(current + 1, 26):
                if freq_arr[c] > 0:
                    return c
            return -1

        def fillSmallest(ans_arr, start, freq_arr):
            pos = start
            for c in range(26):
                while freq_arr[c] > 0:
                    ans_arr[pos] = chr(ord('a') + c)
                    pos += 1
                    freq_arr[c] -= 1

        # try to make the string greater from the current pos
        if i < n:
            greater = findGreater(freq, ord(target[i]) - ord('a'))

            if greater != -1:
                ans[i] = chr(ord('a') + greater)
                freq[greater] -= 1
                
                fillSmallest(ans, i + 1, freq)
                return "".join(ans)

        # backtrack to find the rightmost pos to increase
        i -= 1

        while i >= 0:
            current = ord(ans[i]) - ord('a')

            # put the prev matched character back
            freq[current] += 1

            greater = findGreater(freq, current)

            if greater != -1:
                ans[i] = chr(ord('a') + greater)
                freq[greater] -= 1

                fillSmallest(ans, i + 1, freq)
                return "".join(ans)
            i -= 1
            
        return ""
