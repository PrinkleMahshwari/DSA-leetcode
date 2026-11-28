class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length, minPrice = Integer.MAX_VALUE;

        for (int[] item : items) minPrice = Math.min (minPrice, item[1]);

        int[] freq = new int[n + 1];
        for (int[] item : items) freq[item[0]]++;

        int[] gainFactor = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            int count = 0;
            for (int mult = f; mult <= n; mult += f) count += freq[mult];
            gainFactor[f] = count - 1;
        }

        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = items[i][1]; arr[i][1] = gainFactor[items[i][0]];
        }

        Arrays.sort (arr, (a, b) -> Integer.compare (a[0], b[0]));

        long remaining = budget, ans = 0;
        for (int[] item: arr) {
            int price = item[0], gain = item[1];

            if (price >= 2L * minPrice) break;

            long buy = Math.min ((long) gain, remaining / price);
            ans += 2 * buy;
            remaining -= buy * price;
        }

        ans += remaining / minPrice;

        return (int) ans;
    }
}