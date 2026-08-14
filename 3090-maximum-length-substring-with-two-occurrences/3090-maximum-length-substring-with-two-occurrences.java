class Solution {
    public int maximumLengthSubstring(String s) {
        
        // we are using frequency array as constraints are less and only lower case English alphabets 
        int[] freq = new int[26];

        // the question have "longest substring" keywords that suggest the sliding window because substring is a continuous window inside the string

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {

            int current = s.charAt(right) - 'a'; // get index of current character at right
            freq[current]++; // store occurrence of that character

            // check occurrence of current character is more than 2 times or not, if more than 2 times then shrink window from the left
            while (freq[current] > 2) {
                int removed = s.charAt(left) - 'a'; // get the index of character at left
                freq[removed]--; // remove character at left
                left++; // shrink the window from left
            }

            // update maxlength
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}