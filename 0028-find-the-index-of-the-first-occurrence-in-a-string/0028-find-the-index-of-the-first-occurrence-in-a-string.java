class Solution {
    public int strStr(String haystack, String needle) {

        int n = haystack.length();
        int m = needle.length();

        if (m == 0) return 0;
        if (m > n) return -1;

        // build KMP failure function (lps = "longest proper prefix that is
        // also a suffix") for the needle, so on a mismatch we know exactly
        // how far to fall back without re-checking already-matched characters
        int[] lps = buildLPS(needle);

        int i = 0; // pointer into haystack
        int j = 0; // pointer into needle

        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    return i - m; // full match found, return starting index
                }
            } else if (j > 0) {
                // mismatch after some progress: fall back j using lps,
                // haystack pointer i never moves backward
                j = lps[j - 1];
            } else {
                // no progress made yet, just advance haystack pointer
                i++;
            }
        }

        return -1;
    }

    // lps[i] = length of the longest proper prefix of needle[0..i] that
    // is also a suffix of needle[0..i]
    private int[] buildLPS(String needle) {
        int m = needle.length();
        int[] lps = new int[m];

        int len = 0; // length of the previous longest prefix-suffix match
        int i = 1;

        while (i < m) {
            if (needle.charAt(i) == needle.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else if (len > 0) {
                len = lps[len - 1]; // fall back within the pattern itself
            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }
}