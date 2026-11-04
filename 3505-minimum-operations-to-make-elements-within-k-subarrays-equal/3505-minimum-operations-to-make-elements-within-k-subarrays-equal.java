
import java.util.*;

class Solution {

    class MedianWindow {

        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        int leftSize = 0;
        int rightSize = 0;

        long sumLeft = 0;
        long sumRight = 0;

        int target;

        MedianWindow(int x) {
            target = (x + 1) / 2;
        }

        void addLeft(int val) {
            left.put(val, left.getOrDefault(val, 0) + 1);
            leftSize++;
            sumLeft += val;
        }

        void addRight(int val) {
            right.put(val, right.getOrDefault(val, 0) + 1);
            rightSize++;
            sumRight += val;
        }

        void removeLeft(int val) {
            int count = left.get(val);

            if (count == 1) {
                left.remove(val);
            } else {
                left.put(val, count - 1);
            }

            leftSize--;
            sumLeft -= val;
        }

        void removeRight(int val) {
            int count = right.get(val);

            if (count == 1) {
                right.remove(val);
            } else {
                right.put(val, count - 1);
            }

            rightSize--;
            sumRight -= val;
        }

        void rebalance() {

            // Keep the required number of elements in left
            while (leftSize > target) {
                int val = left.lastKey();

                removeLeft(val);
                addRight(val);
            }

            while (leftSize < target && rightSize > 0) {
                int val = right.firstKey();

                removeRight(val);
                addLeft(val);
            }
        }

        void add(int val) {

            if (leftSize == 0 || val <= left.lastKey()) {
                addLeft(val);
            } else {
                addRight(val);
            }

            rebalance();
        }

        void remove(int val) {

            if (left.containsKey(val)) {
                removeLeft(val);
            } else if (right.containsKey(val)) {
                removeRight(val);
            }

            rebalance();
        }

        long getCost() {

            long median = left.lastKey();

            return median * leftSize - sumLeft
                    + sumRight - median * rightSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Step 1: Calculate the cost of every window
        long[] cost = new long[n - x + 1];

        MedianWindow window = new MedianWindow(x);

        // First window
        for (int i = 0; i < x; i++) {
            window.add(nums[i]);
        }

        cost[0] = window.getCost();

        // Slide the window
        for (int i = x; i < n; i++) {

            window.remove(nums[i - x]);
            window.add(nums[i]);

            cost[i - x + 1] = window.getCost();
        }

        // Step 2: Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {

            dp[i][0] = 0;

            for (int j = 1; j <= k; j++) {

                // Option 1: Skip current element
                dp[i][j] = dp[i - 1][j];

                // Option 2: Select a window ending at i - 1
                if (i >= x && dp[i - x][j - 1] != INF) {

                    long take = dp[i - x][j - 1]
                              + cost[i - x];

                    dp[i][j] = Math.min(dp[i][j], take);
                }
            }
        }

        return dp[n][k];
    }
}
