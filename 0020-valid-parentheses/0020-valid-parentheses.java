class Solution {
    public boolean isValid(String s) {
        
        // stack to store opening brackets
        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {

            // if opening bracket --> push to stack
            if (ch == '(' || ch == '[' || ch == '{')
                stack.push(ch);
            
            // if closing bracket --> check top of stack
            else {

                // case 1: stack empty --> no matching opening
                if (stack.isEmpty())
                    return false;
                
                char top = stack.pop();

                // case 2: mismatch check
                if (ch == ')' && top != '(')
                    return false;
                
                if (ch == ']' && top != '[')
                    return false;
                
                if (ch == '}' && top != '{')
                    return false;
                
            }
        }

        // case 3: stack must be empty at the end
        return stack.isEmpty();
    }
}