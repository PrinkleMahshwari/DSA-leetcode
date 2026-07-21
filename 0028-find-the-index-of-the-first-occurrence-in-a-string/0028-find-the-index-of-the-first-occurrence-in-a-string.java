class Solution {
    public int strStr(String haystack, String needle) {

        int n = haystack.length();
        int m = needle.length();

        if (m == 0) return 0;
        if (m > n) return -1;

        // convert once to char[], avoids repeated bounds-checked charAt()
        // calls into the String object during both the lps build and scan
        char[] h = haystack.toCharArray();
        char[] p = needle.toCharArray();

        int[] lps = buildLPS(p);

        int i = 0, j = 0;

        while (i < n) {
            if (h[i] == p[j]) {
                i++;
                j++;
                if (j == m) return i - m;
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }

        return -1;
    }

    private int[] buildLPS(char[] p) {
        int m = p.length;
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (p[i] == p[len]) {
                lps[i] = ++len;
                i++;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }
}