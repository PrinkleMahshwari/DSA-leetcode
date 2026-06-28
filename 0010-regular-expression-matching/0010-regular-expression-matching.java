class Solution {

    // memo[i][j] stores:
    // true --> s[i:] matches p[j:]
    // false --> does NOT match
    // null --> not computed yet

    Boolean memo[][];

    public boolean isMatch(String s, String p) {

        // initialize memo table
        memo = new Boolean[s.length() + 1][p.length() + 1];

        // start recursion from index 0,0
        return dfs (0, 0, s, p);
    }

    // recursive function meaning:
    // dfs(i ,j) --> does s[i:] match p[j:] ?
    private boolean dfs(int i, int j, String s, String p) {

        // base case 1:
        // if pattern finished, string must also be finished
        if (j == p.length()) { return i == s.length(); }

        // base case 2:
        // check memo (already solved state)
        if (memo[i][j] != null) { return memo[i][j]; }

        // check if current characters match
        boolean first_match = (i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.'));

        boolean result;

        // case 1: next character in pattern is '*'
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {

            // option 1: skip x* occurrence
            boolean skip = dfs(i, j + 2, s, p);

            // option 2: use x* (0 occurrence)
            boolean use = first_match && dfs(i + 1, j, s, p);

            // combine both possiblities
            result = skip || use;
        }
        // case 2: normal match (no '*')
        else {

            // move both i and j if current match
            result = first_match && dfs(i + 1, j + 1, s, p);
        }

        // store result in memo before returning
        memo[i][j] = result;
        return result;
    }
}