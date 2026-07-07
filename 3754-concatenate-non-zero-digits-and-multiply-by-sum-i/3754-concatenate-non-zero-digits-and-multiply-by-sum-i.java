class Solution {
    public long sumAndMultiply(int n) {
        
        // step 1: reverse the original number so we can later process the digits back in their original lef-to-right order
        int reversed = 0;
        int temp = n;

        while (temp > 0) {
            reversed = reversed * 10 + (temp % 10);
            temp /= 10;
        }

        // step 2: build the concatenated non zero number simultaneously compute the sum of its digits
        int concatenatedNumber = 0;
        int digitSum = 0;

        while (reversed > 0) {

            // extract digit in the original left-to-right order
            int digit = reversed % 10;
            reversed /= 10;

            // ignore non zero digits
            if (digit == 0)
                continue;
            
            // append the non zero digits
            concatenatedNumber = concatenatedNumber * 10 + digit;

            // accumulate the digit sum
            digitSum += digit;
        }

        // if the original number was 0, concatenatedNumber and digitSum both remain 0
        return 1L * concatenatedNumber * digitSum;
    }
}