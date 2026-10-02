class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        backtrack(n, 0, 0, current, result);

        return result;
    }

    private void backtrack (int n, int open, int close, StringBuilder current, List<String> result) {
        // complete valid combination
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }

        // add  opening parenthesis
        if (open < n) {
            current.append('(');
            backtrack (n, open + 1, close, current, result);
            current.deleteCharAt(current.length() - 1);
        }

        // add closing parenthesis only when valid
        if (close < open) {
            current.append(')');
            backtrack(n, open, close + 1, current, result);
            current.deleteCharAt(current.length() - 1);
        }
    }
}