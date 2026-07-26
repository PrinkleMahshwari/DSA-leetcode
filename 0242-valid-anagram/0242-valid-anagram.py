class Solution:
    def isAnagram(self, s: str, t: str) -> bool:
        if len(s) != len(t):
            return False
            
        # Using a fixed 26-slot list is faster than a hash dictionary 
        # because it avoids string hashing calculation cycles.
        char_counts = [0] * 26
        
        for i in range(len(s)):
            char_counts[ord(s[i]) - 97] += 1
            char_counts[ord(t[i]) - 97] -= 1
            
        for count in char_counts:
            if count != 0:
                return False
                
        return True
