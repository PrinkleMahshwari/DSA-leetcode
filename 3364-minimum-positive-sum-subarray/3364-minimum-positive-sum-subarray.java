class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size ();

        // step 1: build prefix sum
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums.get (i);

        int ans = Integer.MAX_VALUE;

        // step 2: check all valid subarray length
        for (int i = 0; i < n; i++) {
            for (int len = l; len <= r && i + len <= n; len++) {
                int sum = prefix[i + len] - prefix[i];

                // step 3: consider only positive sum
                if (sum > 0) ans = Math.min (ans, sum); 
            }
        }

        // step 4: return the answer
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}