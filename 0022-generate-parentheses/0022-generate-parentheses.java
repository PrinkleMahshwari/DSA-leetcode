class Solution {
    public List<String> generateParenthesis(int n) {
        
        // store final result of all valid combinations
        List<String> result = new ArrayList<>();

        // use StringBuilder for efficient append and delete operations
        // avoids string immutability overhead (better runtime performance)
        StringBuilder current = new StringBuilder();

        // start DFS backtracking with open = 0, closed = 0
        backtrack(n, 0, 0, current, result);

        return result;
    }

    // DFS Backtracking function
    // open --> number of '(' used so far
    // close --> number of ')' used so far
    private void backtrack(int n, int open, int close,
        StringBuilder current,
        List<String> result) {
        
        // base case: valid combination formed
        if (open == n && close == n) {
            result.add(current.toString());
            return;
        }

        // choice 1: add '(' if we still have remaining open brackets
        if (open < n) {
            current.append('('); // choose
            backtrack(n, open + 1, close, current, result); // explore
            current.deleteCharAt(current.length() - 1); // backtrack (undo)
        }

        // choice 2: add ')' only if it does not break validity
        if (close < open) {
            current.append(')'); // choose
            backtrack(n, open, close + 1, current, result); // explore
            current.deleteCharAt(current.length() - 1); // backtrack (undo)
        }
    }
}