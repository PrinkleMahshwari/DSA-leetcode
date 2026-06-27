class Solution {
    public boolean isPalindrome(int x) {
        // negative check
        if (x < 0) return false;

        // trailing zero check
        if (x % 10 == 0 && x != 0) return false;

        int reversed = 0;
        
        //reverse half
        while (x > reversed) {
            reversed = reversed * 10 + x % 10; // get and add last digit
            x /= 10; // remove last digit
        }

        // compare (even + odd length)
        return (x == reversed || x == reversed / 10);
    }
}