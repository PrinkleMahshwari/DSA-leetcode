class Solution:
    def maximumLengthSubstring(self, s: str) -> int:
        # Frequency array for 26 lowercase English letters
        freq = [0] * 26

        left = 0
        maxLength = 0

        for right in range(len(s)):
            current = ord(s[right]) - ord('a')  # get index of current character at right
            freq[current] += 1  # store occurrence of that character

            # check occurrence of current character is more than 2 times or not
            while freq[current] > 2:
                removed = ord(s[left]) - ord('a')  # get the index of character at left
                freq[removed] -= 1  # remove character at left
                left += 1  # shrink the window from left

            # update maxlength
            maxLength = max(maxLength, right - left + 1)

        return maxLength
