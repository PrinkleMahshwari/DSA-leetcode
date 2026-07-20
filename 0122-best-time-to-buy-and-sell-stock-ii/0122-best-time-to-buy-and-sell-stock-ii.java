class Solution {
    public int maxProfit(int[] prices) {
        
        int n = prices.length;
        int profit = 0;

        for (int i = 1; i < n; i++) {

            // ever positive increase contributes to the maxium profit
            if (prices[i] > prices[ i - 1])
                profit += prices[i] - prices[i - 1];
        }

        return profit;
    }
}