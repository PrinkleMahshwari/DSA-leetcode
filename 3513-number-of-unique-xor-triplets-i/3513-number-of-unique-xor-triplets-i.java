class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;
        
        // Base edge cases for small permutations
        if (n == 1) return 1;
        if (n == 2) return 2;
        
        // For n >= 3, the span of unique integers reachable is [0, 2^(msb + 1) - 1]
        // 32 - Integer.numberOfLeadingZeros(n) calculates (msb + 1) directly
        int power = 32 - Integer.numberOfLeadingZeros(n);
        
        return 1 << power;
    }
}
