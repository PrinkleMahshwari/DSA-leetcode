import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();

        remove(s, result, 0, 0, '(', ')');

        return result;
    }

    private void remove(
        String s,
        List<String> result,
        int start,
        int checkStart,
        char open,
        char close
    ) {
        int balance = 0;

        for (int i = checkStart; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == open) {
                balance++;
            } else if (ch == close) {
                balance--;
            }

            // Found an invalid closing parenthesis
            if (balance < 0) {
                for (int j = start; j <= i; j++) {

                    // Skip duplicate removals
                    if (j > start && s.charAt(j) == s.charAt(j - 1)) {
                        continue;
                    }

                    // Remove this closing parenthesis
                    if (s.charAt(j) == close) {
                        String next = s.substring(0, j)
                                   + s.substring(j + 1);

                        remove(
                            next,
                            result,
                            j,
                            i,
                            open,
                            close
                        );
                    }
                }

                return;
            }
        }

        // No invalid closing parentheses remain.
        // Reverse and check the opposite direction.
        String reversed = new StringBuilder(s).reverse().toString();

        if (open == '(') {
            remove(
                reversed,
                result,
                0,
                0,
                ')',
                '('
            );
        } else {
            result.add(reversed);
        }
    }
}