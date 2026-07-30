class Solution:
    def minimumPushes(self, word: str) -> int:
        # Step 1: Record character frequencies using a fixed 26-slot layout
        counts = [0] * 26
        for char in word:
            counts[ord(char) - 97] += 1
            
        # Step 2: Sort in descending order to prioritize high-frequency letters
        counts.sort(reverse=True)
        
        # Step 3: Accumulate pushes using greedy mathematical tiers
        total_pushes = 0
        for i in range(26):
            if counts[i] == 0:
                break
            total_pushes += counts[i] * ((i // 8) + 1)
            
        return total_pushes
