class Solution:
    def removeInvalidParentheses(self, s: str) -> List[str]:
        result = []
        
        def remove(string: str, start: int, check_start: int, open_ch: str, close_ch: str):
            balance = 0
            
            for i in range(check_start, len(string)):
                ch = string[i]
                if ch == open_ch:
                    balance += 1
                elif ch == close_ch:
                    balance -= 1
                    
                # Found an invalid closing parenthesis
                if balance < 0:
                    for j in range(start, i + 1):
                        # Skip duplicate removals
                        if j > start and string[j] == string[j - 1]:
                            continue
                            
                        # Remove this closing parenthesis
                        if string[j] == close_ch:
                            next_str = string[:j] + string[j+1:]
                            remove(next_str, j, i, open_ch, close_ch)
                    return
            
            # No invalid closing parentheses remain.
            # Reverse and check the opposite direction.
            reversed_str = string[::-1]
            if open_ch == '(':
                remove(reversed_str, 0, 0, ')', '(')
            else:
                result.append(reversed_str)
                
        remove(s, 0, 0, '(', ')')
        return result
