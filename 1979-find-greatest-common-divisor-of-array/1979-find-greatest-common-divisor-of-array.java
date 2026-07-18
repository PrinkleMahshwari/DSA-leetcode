class Solution {
    public int findGCD(int[] nums) {
        
        // single pass to find min and max, avoids sorting the whole array
        int mn = nums[0], mx = nums[0];
        for (int num : nums) {
            if (num < mn) mn = num;
           else if (num > mx) mx = num;
        }

        return gcd(mn, mx);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            a %= b; // combined compare+swaps into fewer ops
            int t = a; a = b; b = t;
        }
        
        return a;
    }
}