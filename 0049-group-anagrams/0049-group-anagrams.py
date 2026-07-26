from collections import defaultdict

class Solution:
    def groupAnagrams(self, strs: list[str]) -> list[list[str]]:
        anagramGroups = defaultdict(list)
        for s in strs:
            # Tuples are hashable and act perfectly as dictionary keys in Python
            key = tuple(sorted(s))
            anagramGroups[key].append(s)
        return list(anagramGroups.values())
