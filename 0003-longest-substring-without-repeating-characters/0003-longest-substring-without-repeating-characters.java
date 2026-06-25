class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int maxLength = 0;

        // array to store the last seen index + 1 of each character
        // size 128 covers standard ASCII
        int[] charIndexCache = new int[128];

        for (int right = 0, left = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // if the character was seen inside current window
            // jump the left boundary past its previous position
            left = Math.max(charIndexCache[currentChar], left);

            // calculate window size and update maximum length
            maxLength = Math.max(maxLength, right - left + 1);

            // stor the next index position (right + 1) from this character
            charIndexCache[currentChar] = right + 1;
        }
        return maxLength;
    }
}