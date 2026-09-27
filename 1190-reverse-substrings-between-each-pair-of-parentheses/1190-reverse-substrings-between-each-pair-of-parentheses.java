class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();
        int[] pair = new int[n];

        Stack<Integer> stack = new Stack<>();

        // find matching parentheses
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int open = stack.pop();

                pair[open] = i;
                pair[i] = open;
            }
        }
        StringBuilder result = new StringBuilder();

        int i = 0, direction = 1;

        while (i >= 0 && i < n) {
            char current = s.charAt(i);

            if (current == '(' || current == ')') {
                // jump to matching parentheses
                i = pair[i];

                // reverse traversal direction
                direction = -direction;
            } else {
                result.append(current);
            }

            i += direction;
        }

        return result.toString();
    }
}