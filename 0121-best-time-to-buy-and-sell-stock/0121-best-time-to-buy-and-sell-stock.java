class Solution {
    public int maxProfit(int[] prices) {
        
        int n = prices.length;

        int minPrice = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < n; i++) {

            // better buying opporunity found
            if (prices[i] < minPrice)
                minPrice = prices[i];
            
            // sell today
            int profit = prices[i] - minPrice;

            // update answer
            if (profit > maxProfit)
                maxProfit = profit;

        }

        return maxProfit;
    }
}