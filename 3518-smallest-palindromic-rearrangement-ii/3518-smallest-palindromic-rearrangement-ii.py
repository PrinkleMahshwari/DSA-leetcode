class Solution:
    def smallestPalindrome(self, s: str, k: int) -> str:
        n = len(s)
        total_counts = [0] * 26
        for char in s:
            total_counts[ord(char) - 97] += 1
            
        half_len = n // 2
        half_counts = [0] * 26
        mid_char = ""
        
        for i in range(26):
            half_counts[i] = total_counts[i] // 2
            if total_counts[i] % 2 != 0:
                mid_char = chr(i + 97)
                
        first_half = [""] * half_len
        remaining_slots = half_len
        
        # Build the first half position by position
        for pos in range(half_len):
            found = False
            for c in range(26):
                if half_counts[c] > 0:
                    half_counts[c] -= 1
                    remaining_slots -= 1
                    
                    # Calculate remaining permutations capping at k + 1 to avoid overflow
                    p = self.getPermutations(half_counts, remaining_slots, k)
                    
                    if p >= k:
                        first_half[pos] = chr(c + 97)
                        found = True
                        break # Character locked into place
                    else:
                        k -= p
                        half_counts[c] += 1
                        remaining_slots += 1
            if not found:
                return "" # Fewer than k permutations available
                
        if k > 1:
            return ""
            
        # Reconstruct mirrored string via fast python slices
        half_str = "".join(first_half)
        return half_str + mid_char + half_str[::-1]

    # Helper to calculate multinomial coefficient capped at threshold
    def getPermutations(self, counts: list, total: int, threshold: int) -> int:
        res = 1
        current_total = 1
        
        for i in range(26):
            cnt = counts[i]
            for j in range(1, cnt + 1):
                res = (res * current_total) // j
                current_total += 1
                if res > threshold:
                    return threshold + 1 # Cap to avoid integer overflow
        return res
