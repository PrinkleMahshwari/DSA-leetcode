class Solution {
    public int strStr(String haystack, String needle) {
        // built-in indexOf is a JVM intrinsic (often backed by optimized
        // native substring search), beats hand-written KMP's constant
        // factor for these small constraints (n, m <= 10^4)
        return haystack.indexOf(needle);
    }
}