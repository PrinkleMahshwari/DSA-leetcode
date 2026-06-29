class Solution {
    public int romanToInt(String s) {
        
        // store Roman symbol and it's correspoding integer values
        HashMap<Character, Integer> map = new HashMap<>();

        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        // store final integer value
        int result = 0;

        // traverse every Roman character
        for (int i = 0; i < s.length(); i++) {

            // get current Roman value
            int current = map.get(s.charAt(i));
            
            // check whether next character exists
            if (i + 1 < s.length()) {

                // get next Roman value
                int next = map.get(s.charAt(i + 1));

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
}