class Solution {
    public int numberOfSubstrings(String s) {
        
        // store the last seen index of characters a, b, and c
        int lastA = -1;
        int lastB = -1;
        int lastC = -1;

        // store final count of substrings
        int result = 0;

        // traverse every character in the string
        for (int i = 0; i < s.length(); i++) {

            // get current character
            char current = s.charAt(i);

            // update the last seen index for the current character
            if (current == 'a') {
                lastA = i;
            }
            else if (current == 'b') {
                lastB = i;
            }
            else if (current == 'c') {
                lastC = i;
            }

            // check whether all three characters have been seen at least once
            if (lastA != -1 && lastB != -1 && lastC != -1) {

                // find the smallest index among the last seen positions
                int minIndex = Math.min(lastA, Math.min(lastB, lastC));

                // valid substrings can start anywhere from index 0 up to minIndex
                result += minIndex + 1;
            }
        }
        
        // return final count
        return result;
    }
}
