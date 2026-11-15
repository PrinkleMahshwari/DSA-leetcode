class Solution {
    public int numOfSubarrays(int[] arr) {
        // Approach: Count Prefix Parity
        int MOD = 1_000_000_007;
        long even = 1, odd = 0, ans = 0;
        int prefixParity = 0;

        for (int num : arr) {
            // update prefix sum parity
            prefixParity ^= (num & 1);

            if (prefixParity == 1) {
                // Odd Prefix: pair with the previous even prefixes
                ans = (ans + even) % MOD;
                odd++;
            } else {
                // Even Prefix: pair with previous odd prefixes
                ans = (ans + odd) % MOD;
                even++;
            }
        }

        return (int) ans;
    }
}