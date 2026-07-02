class Solution {
    public boolean isValid(String s) {
        int n = s.length();

        // an odd-length string can never be balanced
        if (n % 2 != 0)
            return false;
        
        // Primitive array stack instead of java.util.Stack
        // allocating a fixed primitive array avoids object boxing and synchronization overhead
        char[] stack = new char[n];
        int top = 0; // acts as our stack pointer (index where next element goes)
        
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            // push the expected closing bracket instead of the opening one
            // this simplifies the mismatch validation checks down to a single comparison
            if (ch == '(')
                stack[top++] = ')';
            
            else if (ch == '[')
                stack[top++] = ']';
            
            else if (ch == '{')
                stack[top++] = '}';
            
            // if it's a closing brakcet:
            else {
                // case 1: stack is empty (top == 0) --> no matching opening bracket
                // case 2: mismatch --> the popped character doesn't match current closing character
                if (top == 0 || stack[--top] != ch)
                    return false;
            }
        }

        // case 3: The stack pointer must return to 0 if all brackets were perfectly matched
        return top == 0;
    }
}