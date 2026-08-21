class Solution {
    public long findKthSmallest(int[] coins, int k) {
        
        long minCoin = Long.MAX_VALUE;

        for (int coin : coins)
            minCoin = Math.min(minCoin, coin);
        
        long left = 1;
        long right = minCoin * k;

        while (left < right) {
            long mid = left + (right - left) / 2;

            if (count(mid, coins, 0, 1, 0) >= k)
                right = mid;
            else
                left = mid + 1;
            
        }

        return left;
    }

    // helper function for count
    private long count(long x, int[] coins, int index, long lcm, int selected) {
        long result = 0;

        for (int i = index; i < coins.length; i++) {

            long newLcm = lcm(lcm, coins[i]);

            if (newLcm > x)
                continue;
            
            long contribution = x / newLcm;

            if ((selected + 1) % 2 == 1)
                result += contribution;
            
            else
                result -= contribution;
            
            result += count(x, coins, i + 1, newLcm, selected + 1);
        }

        return result;
    }

    // helper function for gcd
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    // helper function for lcm
    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}