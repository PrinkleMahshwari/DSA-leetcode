class Solution {
    public String longestCommonPrefix(String[] strs) {

        // edge case: empty array
        if (strs == null || strs.length == 0)
            return "";

        // take first string as reference
        String first = strs[0];

        // loop through each character of first string
        for (int i = 0; i < first.length(); i++) {

            char ch = first.charAt(i);

            // compare with all other strings
            for (int j = 1; j < strs.length; j++) {

                // if index out of bounds Or mismatch --> stop
                if (i >= strs[j].length() || strs[j].charAt(i) != ch) 
                    return first.substring(0, i);

            }
        }

        // if full first string matches all
        return first;
    }
}