class Solution:
    def minimumPushes(self, word: str) -> int:
        freq = [0] * 26
        
        # Linear character scan bypassing intermediate data structures
        for char in word:
            freq[ord(char) - 97] += 1
            
        # In-place primitive list sorting
        freq.sort()
        
        total_pushes = 0
        key_index = 0
        
        # Loop backwards over the 26 entries
        for i in range(25, -1, -1):
            if freq[i] == 0:
                break
                
            push_cost = (key_index // 8) + 1
            total_pushes += freq[i] * push_cost
            key_index += 1
            
        return total_pushes
