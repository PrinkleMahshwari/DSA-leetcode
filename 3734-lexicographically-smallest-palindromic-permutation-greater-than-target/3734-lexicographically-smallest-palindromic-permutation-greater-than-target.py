class Solution:
    def lexPalindromicPermutation(self, s: str, target: str) -> str:
        n = len(s)

        # Count characters
        freq = [0] * 26
        for c in s:
            freq[ord(c) - ord('a')] += 1

        # Check if a palindrome is possible
        odd = 0
        middle = -1
        for i in range(26):
            if (freq[i] & 1) == 1:
                odd += 1
                middle = i

        if odd > 1:
            return ""

        halfLen = n // 2

        # Special case: n = 1
        if halfLen == 0:
            only = chr(ord('a') + middle)
            return only if only > target else ""

        # Frequency of characters available for the first half
        halfFreq = [0] * 26
        for i in range(26):
            halfFreq[i] = freq[i] // 2

        # target's first half
        targetHalf = target[:halfLen]

        def buildPalindrome(half: list, middle_char_idx: int, total_len: int) -> str:
            res = list(half)
            if (total_len & 1) == 1:
                res.append(chr(ord('a') + middle_char_idx))
            res.extend(half[::-1])
            return "".join(res)

        # First possibility: Try to construct exactly targetHalf
        remaining = list(halfFreq)
        canMatch = True

        for i in range(halfLen):
            c = ord(targetHalf[i]) - ord('a')
            if remaining[c] == 0:
                canMatch = False
                break
            remaining[c] -= 1

        if canMatch:
            half = list(targetHalf)
            candidate = buildPalindrome(half, middle, n)
            if candidate > target:
                return candidate

        # Second possibility: Find the smallest half strictly greater than targetHalf
        def findNextHalf(target_str: str, original_freq: list) -> list:
            length = len(target_str)
            if length == 0:
                return None

            freq_copy = list(original_freq)

            # Match target from left to right as much as possible
            matched = 0
            while matched < length:
                c = ord(target_str[matched]) - ord('a')
                if freq_copy[c] == 0:
                    break
                freq_copy[c] -= 1
                matched += 1

            pivot = matched - 1
            if matched < length:
                pivot = matched

            # Try every possible pivot from right to left
            for p in range(pivot, -1, -1):
                if p < matched:
                    restored = ord(target_str[p]) - ord('a')
                    freq_copy[restored] += 1

                wanted = ord(target_str[p]) - ord('a')

                # Find smallest available character strictly greater than target[p]
                for c in range(wanted + 1, 26):
                    if freq_copy[c] == 0:
                        continue

                    result = [''] * length

                    # Prefix before pivot stays equal to target
                    for i in range(p):
                        result[i] = target_str[i]

                    # Increase at pivot
                    result[p] = chr(ord('a') + c)
                    freq_copy[c] -= 1

                    # Fill the suffix with the smallest characters
                    pos = p + 1
                    for x in range(26):
                        while freq_copy[x] > 0:
                            result[pos] = chr(ord('a') + x)
                            pos += 1
                            freq_copy[x] -= 1

                    return result
            return None

        nextHalf = findNextHalf(targetHalf, halfFreq)
        if nextHalf is None:
            return ""

        return buildPalindrome(nextHalf, middle, n)
