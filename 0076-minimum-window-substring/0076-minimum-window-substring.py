class Solution:
    def minWindow(self, s: str, t: str) -> str:
        if not s or not t or len(s) < len(t):
            return ""

        # Using a flat 128-slot list for ASCII indexing is much faster
        # than a standard dictionary in competitive sliding windows.
        target_counts = [0] * 128
        window_counts = [0] * 128

        required_matches = 0
        for char in t:
            idx = ord(char)
            if target_counts[idx] == 0:
                required_matches += 1
            target_counts[idx] += 1

        left = 0
        right = 0
        formed_matches = 0

        min_len = float('inf')
        min_left_start = 0

        s_len = len(s)

        while right < s_len:
            right_char_idx = ord(s[right])
            window_counts[right_char_idx] += 1

            if target_counts[right_char_idx] > 0 and window_counts[right_char_idx] == target_counts[right_char_idx]:
                formed_matches += 1

            while formed_matches == required_matches:
                current_len = right - left + 1
                if current_len < min_len:
                    min_len = current_len
                    min_left_start = left

                left_char_idx = ord(s[left])
                window_counts[left_char_idx] -= 1

                if target_counts[left_char_idx] > 0 and window_counts[left_char_idx] < target_counts[left_char_idx]:
                    formed_matches -= 1
                left += 1
            
            right += 1

        return "" if min_len == float('inf') else s[min_left_start : min_left_start + min_len]
