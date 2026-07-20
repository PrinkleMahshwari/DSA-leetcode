class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n < 2) return 0; // Guard clause for arrays smaller than 2 elements

        int minPrice = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < n; i++) {
            if (prices[i] < minPrice) {
                // Better buying opportunity: update minimum and skip profit check
                minPrice = prices[i];
            } else {
                // Current price is higher than minPrice: calculate potential profit
                int profit = prices[i] - minPrice;
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }

        return maxProfit;
    }
}
