class Solution {
    public int maxProduct(int n) {
        
        int max1 = 0; // tracks the largest digit
        int max2 = 0; // tracks the second largest digit

        while (n > 0) {
            int digit = n % 10; // extract the last digit
            n /= 10; // remove the last digit

            if (digit > max1) {
                max2 = max1; //the old largest becomes the second largest
                max1 = digit; // update new largest
            } else if (digit > max2) {
                max2 = digit; // update the second largest
            }
        }

        return max1 * max2;
    }
}