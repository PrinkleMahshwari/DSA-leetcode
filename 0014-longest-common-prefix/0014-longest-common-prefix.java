class Solution {
    public String longestCommonPrefix(String[] strs) {

        // take first string as initial prefix
        String prefix = strs[0];

        // start checking from second starting
        for (int i = 1; i < strs.length; i++) {

            // current word
            String word = strs[i];

            // shrink prefix until it matches current word
            while (!word.startsWith(prefix)) {

                // remove last character from prefix
                // because it is not matching
                prefix = prefix.substring(0, prefix.length() - 1);

                // if prefix becomes empty, no common prefix exist
                if (prefix.isEmpty())
                    return "";

            }
        }

        // return final prefix after all matches
        return prefix;
    }
}