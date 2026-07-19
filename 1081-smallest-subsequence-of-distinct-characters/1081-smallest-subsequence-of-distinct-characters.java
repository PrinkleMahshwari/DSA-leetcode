class Solution {
    public String smallestSubsequence(String s) {

        int n = s.length();
        char[] chars = s.toCharArray();

        // lastIndex[c] = last position where character c appears,
        // needed to know how long we can still "wait" before a char is gone forever
        int[] lastIndex = new int[26];
        for (int i = 0; i < n; i++) {
            lastIndex[chars[i] - 'a'] = i;
        }

        // monotonic stack (as a char array + pointer) builds the result,
        // avoids ArrayDeque/StringBuilder churn from repeated push/pop
        char[] stack = new char[26]; // at most 26 distinct lowercase letters
        int top = -1;

        boolean[] onStack = new boolean[26];

        for (int i = 0; i < n; i++) {
            int c = chars[i] - 'a';

            // this character already placed, skip -- each distinct char
            // appears exactly once in the final answer
            if (onStack[c]) continue;

            // pop while: top of stack is greater than current char (so popping
            // helps lexicographic order), AND that popped char still appears
            // later in the string (so we can safely re-add it after)
            while (top >= 0 && stack[top] > c && lastIndex[stack[top]] > i) {
                onStack[stack[top]] = false;
                top--;
            }

            stack[++top] = (char) c;
            onStack[c] = true;
        }

        StringBuilder result = new StringBuilder(top + 1);
        for (int i = 0; i <= top; i++) {
            result.append((char) ('a' + stack[i]));
        }

        return result.toString();
    }
}