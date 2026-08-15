class Solution {
    public int longestSubsequence(int[] nums) {
        int xor = 0;
        boolean hasNonZero = false;
        
        // calculate the xor of entire array
        // if total xor != 0 then answer = n
        // otherwise 
        // if there is at least one non zero element answer = n-1
        // else answer = 0
        // we don't need to construct the subsequence
        // Key point of the problem:
        // If totalXor is 0, removing one element (non zero) makes the remaining xor equalt to that element

        for (int num : nums) {
            xor ^= num;

            if (num != 0)
                hasNonZero = true;
        }

        if (xor != 0)
            return nums.length;
        
        return hasNonZero ? nums.length - 1 : 0;
    }
}