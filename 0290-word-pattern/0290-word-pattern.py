class Solution:
    def wordPattern(self, pattern: str, s: str) -> bool:
        words = s.split()
        if len(pattern) != len(words):
            return False
            
        charLastSeen = [0] * 26
        wordLastSeen = {}
        
        for i in range(len(pattern)):
            charIdx = ord(pattern[i]) - 97
            word = words[i]
            
            lastCharPos = charLastSeen[charIdx]
            lastWordPos = wordLastSeen.get(word, 0)
            
            if lastCharPos != lastWordPos:
                return False
                
            charLastSeen[charIdx] = i + 1
            wordLastSeen[word] = i + 1
            
        return True
