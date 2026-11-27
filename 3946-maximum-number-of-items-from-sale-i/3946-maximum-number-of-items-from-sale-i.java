class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length, maxFactor = 0, minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            maxFactor = Math.max (maxFactor, item[0]);
            minPrice = Math.min (minPrice, item[1]);
        }

        int[] freq = new int[maxFactor + 1];
        for (int[] item : items) freq[item[0]]++;

        int[] free = new int[n];
        for (int i = 0; i < n; i++) {
            int factor = items[i][0], count = 0;
            for (int mult = factor; mult <= maxFactor; mult += factor) count += freq[mult];

            free[i] = count - 1;
        }

        int[] dp = new int[budget + 1];
        Arrays.fill (dp, -1);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            int price = items[i][1], value = 1 + free[i];
            for (int c = budget; c >= price; c--)
                if (dp[c - price] != -1) dp[c] = Math.max (dp[c], dp[c - price] + value);
        }

        int ans = 0;
        for (int c = 0; c <= budget; c++)
            if (dp[c] != -1) ans = Math.max (ans, dp[c] + (budget - c) / minPrice);

        return ans;
    }
}