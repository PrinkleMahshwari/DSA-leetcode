class Solution {
    public int romanToInt(String s) {
        
        // store final integer value
        int result = 0;

        // traverse every Roman character
        for (int i = 0; i < s.length(); i++) {

            // get current Roman value
            int current = getValue(s.charAt(i));
            
            // check whether next character exists
            if (i + 1 < s.length()) {

                // get next Roman value
                int next = getValue(s.charAt(i + 1));

                // subtractive case
                // current symbol is smaller than next symbol
                // e.g., IV --> 1 < 5, CM --> 100 < 1000
                if (current < next) {
                    result -= current;
                }
                // normal Roman notation
                else {
                    result += current;
                }
            }
            // last character
            // no next character exists, so always add it
            else {
                result += current;
            }
        }
        
        // return final integer
        return result;
    }

    // helper method to retrieve integer values instead of HashMap
    private int getValue(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
}
