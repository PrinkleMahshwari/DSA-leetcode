import java.util.*;

class Solution {
    public List<String> generateParenthesis(int n) {

        // store final result of all valid combinations
        List<String> result = new ArrayList<>();

        // use a fixed-size char array instead of StringBuilder
        // total length is always 2*n for a valid combination, so no resizing ever needed
        char[] current = new char[2 * n];

        // start DFS backtracking with open = 0, close = 0, position = 0
        backtrack(n, 0, 0, 0, current, result);

        return result;
    }

    // DFS Backtracking function
    // open  --> number of '(' used so far
    // close --> number of ')' used so far
    // pos   --> current index being filled in the char array
    private void backtrack(int n, int open, int close, int pos,
        char[] current,
        List<String> result) {

        // base case: valid combination formed, pos will equal 2*n
        if (open == n && close == n) {
            result.add(new String(current));
            return;
        }

        // choice 1: add '(' if we still have remaining open brackets
        if (open < n) {
            current[pos] = '('; // choose
            backtrack(n, open + 1, close, pos + 1, current, result); // explore
            // no explicit undo needed, next choice just overwrites current[pos]
        }

        // choice 2: add ')' only if it does not break validity
        if (close < open) {
            current[pos] = ')'; // choose
            backtrack(n, open, close + 1, pos + 1, current, result); // explore
            // no explicit undo needed, next choice just overwrites current[pos]
        }
    }
}