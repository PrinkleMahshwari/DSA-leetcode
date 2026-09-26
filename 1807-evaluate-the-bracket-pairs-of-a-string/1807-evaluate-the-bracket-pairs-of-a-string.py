class Solution:
    def evaluate(self, s: str, knowledge: list[list[str]]) -> str:
        # store key-value pairs in a hash map
        knowledge_map = {}
        for key, value in knowledge:
            knowledge_map[key] = value

        result = []
        i = 0
        n = len(s)

        while i < n:
            # normal character
            if s[i] != '(':
                result.append(s[i])
                i += 1
                continue

            # find closing bracket
            j = i + 1
            while s[j] != ')':
                j += 1

            # extract key
            key = s[i + 1:j]

            # replace with value or '?'
            result.append(knowledge_map.get(key, "?"))

            # move after ')'
            i = j + 1

        return "".join(result)
