class Solution {
    public int reverse(int x) {
        // initialize reverse with 0
        int rev = 0;

        while (x != 0) {
            // get last digit
            int digit = x % 10;

            // check postive overflow
            if (rev > Integer.MAX_VALUE / 10 || rev == Integer.MAX_VALUE / 10 && digit > 7) { return 0;}

            // check negative overflow
            if (rev < Integer.MIN_VALUE / 10 || rev == Integer.MIN_VALUE / 10 && digit < -8) { return 0;}

            // reverse the integer
            rev = rev * 10 + digit;

            // remove last digit
            x /= 10;
        }
        return rev;
    }
}