class Solution {
    public int findGCD(int[] nums) {
        
        // single pass to find min and max, avoids sorting the whole array
        int mn = nums[0], mx = nums[0];
        for (int num : nums) {
            if (num < mn) mn = num;
            if (num > mx) mx = num;
        }

        return gcd(mn, mx);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        
        return a;
    }
}