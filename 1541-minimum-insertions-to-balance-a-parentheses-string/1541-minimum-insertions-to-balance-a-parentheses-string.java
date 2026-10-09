class Solution {
    public int minInsertions(String s) {
        int open = 0;
        int insertions = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                open++;
            } else {
                //if this ')' is not followed by another ')'
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') i++;
                else insertions++;

                // no opening parenthesis available to match this
                if (open == 0) insertions++;
                else open--;
            }
        }

        // each remaining '(' needs two ')'
        return insertions + open * 2;
    }
}