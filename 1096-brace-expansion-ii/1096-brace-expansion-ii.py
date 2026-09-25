class Solution:
    def braceExpansionII(self, expression: str) -> list[str]:
        self.expression = expression
        self.index = 0

        # Fixed: Internal helper structures cleanly nested to share recursive frame scope
        def parseExpression() -> set:
            result = parseTerm()
            while self.index < len(self.expression) and self.expression[self.index] == ',':
                self.index += 1  # skip ','
                next_set = parseTerm()
                result.update(next_set)
            return result

        def parseTerm() -> set:
            result = {""}
            while (self.index < len(self.expression) and 
                   self.expression[self.index] != '}' and 
                   self.expression[self.index] != ','):
                next_set = parseFactor()
                result = combine(result, next_set)
            return result

        def parseFactor() -> set:
            if self.expression[self.index] == '{':
                self.index += 1  # skip '{'
                result = parseExpression()
                self.index += 1  # skip '}'
                return result
            
            result = {self.expression[self.index]}
            self.index += 1
            return result

        def combine(first: set, second: set) -> set:
            result = set()
            for a in first:
                for b in second:
                    result.add(a + b)
            return result

        result_set = parseExpression()
        return sorted(list(result_set))
