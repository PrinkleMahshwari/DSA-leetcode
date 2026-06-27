class Solution {
    public int myAtoi(String s) {
        // initialize variables
        int state = 0; // start
        int sign = 1;
        long result = 0;

        for (int  i = 0; i < s.length(); i++) {
            // store character
            char c = s.charAt(i);
            int type;

            // classify inputs
            if (c == ' ') type = 0;
            else if (c == '+' || c == '-') type = 1;
            else if (c >= '0' && c <= '9') type = 2;
            else type = 3;

            // state machine
            if (state == 0) { // start

                if (type == 0) continue;
                else if (type == 1) {
                    state = 1;
                    sign = (c == '-') ? -1 : 1;
                }
                else if (type == 2) {
                    state = 2;
                    result = c - '0';
                }
                else break;
            }

            else if (state == 1) { // Sign
                if (type == 2) {
                    state = 2;
                    result = c - '0';
                }
                else break;
            }

            else if (state == 2) { // number 
                if (type == 2) {

                    result = result * 10 + (c - '0');

                    // overflow check

                    if (sign == 1 && result > Integer.MAX_VALUE)
                        return Integer.MAX_VALUE;

                    if (sign == -1 && -result < Integer.MIN_VALUE)
                        return Integer.MIN_VALUE;
                }
                else break;
            }
        }
        return (int) (sign * result);
    }
}