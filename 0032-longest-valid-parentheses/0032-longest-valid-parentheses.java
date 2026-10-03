class Solution {
    public int longestValidParentheses(String s) {
        int maxLength = 0;
        int[] stack = new int[s.length() + 1];
        int top = 0;

        // boundary before the string starts
        stack[top] = -1;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack[++top] = i;
            } else {
                top--;

                // unmatched ')'
                if (top < 0) {
                    top = 0;
                    stack[top] = i;
                } else {
                    maxLength = Math.max(maxLength, i - stack[top]);
                }
            }
        }

        return maxLength;
    }
}