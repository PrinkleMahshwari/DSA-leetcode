class Solution {
    public int climbStairs(int n) {
        if (n <= 2) return n;
        
        int prev2 = 1; // Ways to reach 1 step below
        int prev1 = 2; // Ways to reach 2 steps below
        
        for (int i = 3; i <= n; i++) {
            int currentWays = prev1 + prev2;
            prev2 = prev1;
            prev1 = currentWays;
        }
        
        return prev1;
    }
}
