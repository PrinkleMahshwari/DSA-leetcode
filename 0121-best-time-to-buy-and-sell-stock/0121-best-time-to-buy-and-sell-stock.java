class Solution {
    public int maxProfit(int[] prices) {
        
        int n = prices.length;

        if (n < 2) return 0; // guard clause for arrays smaller than 2 elements

        int minPrice = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < n; i++) {

            // better buying opportunity found
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                // sell today
                int profit = prices[i] - minPrice;

                // update answer
                if (profit > maxProfit)
                    maxProfit = profit;
            }        
        }

        return maxProfit;
    }
}