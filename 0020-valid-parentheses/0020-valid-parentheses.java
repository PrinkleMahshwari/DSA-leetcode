class Solution {
    public boolean isValid(String s) {
        char[] stack = new char[s.length()];
        int top = -1;

        for (char ch : s.toCharArray()) {

            // opening brackets
            if (ch == '(' || ch == '[' || ch == '{') {
                stack[++top] = ch;
            } 
            // closing bracket
            else {
                if (top == -1) return false;

                char open = stack[top];

                if ((ch == ')' && open != '(') || (ch == ']' && open != '[') || (ch == '}' && open != '{')) return false;

                top--;
            }
        }

        return top == -1;
    }
}