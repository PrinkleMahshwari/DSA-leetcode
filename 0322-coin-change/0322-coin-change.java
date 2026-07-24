import java.util.Arrays;

class Solution {
    public int coinChange(int[] coins, int amount) {
        // Base case: No money requires zero coins
        if (amount == 0) return 0;
        
        // dp[i] will store the minimum coins needed for amount i
        int[] dp = new int[amount + 1];
        
        // Fill the array with a sentinel value (infinity placeholder)
        Arrays.fill(dp, amount + 1);
        dp[0] = 0; // 0 coins needed to make 0 amount
        
        // Outer loop builds up combinations for every amount up to target
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                // Only evaluate the coin if it doesn't exceed the sub-amount
                if (i >= coin) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        // If the value wasn't updated, the amount cannot be made up
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
