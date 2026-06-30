class Solution {
    public int numberOfSubstrings(String s) {
        
        int n = s.length();

        // frequency array for a, b, c
        int[] freq = new int[3];

        int left = 0;
        int right = 0;

        int count = 0;

        // expand window using right pointer
        while (right < n) {

            // include current character in window
            freq[s.charAt(right) - 'a']++;

            // check if window is valid (contain a, b, c)
            while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {

                // all substrings starting from left and ending from right to n-1 are valid
                count += (n - right);

                // shrink window form left
                freq[s.charAt(left) - 'a']--;
                left++;
            }

            // expand window
            right++;
        }

        return count;
    }
}