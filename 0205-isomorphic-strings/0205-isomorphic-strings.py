class Solution:
    def isIsomorphic(self, s: str, t: str) -> bool:
        # Array size 256 acts as a fast primitive lookup structure for ASCII characters
        mapS = [0] * 256
        mapT = [0] * 256
        
        for i in range(len(s)):
            charS, charT = ord(s[i]), ord(t[i])
            
            if mapS[charS] != mapT[charT]:
                return False
                
            mapS[charS] = i + 1
            mapT[charT] = i + 1
            
        return True
