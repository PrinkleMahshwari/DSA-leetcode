class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] answer = new long[k];
        long[] dp = new long[k];

        for (int num : nums) {
            int value = num % k;
            long[] next = new long[k];

            // start a new subarray with nums[i]
            next[value]++;

            // extend evry subarray ending at the previous index
            for (int r = 0; r < k; r++) {
                if (dp[r] > 0) {
                    int newRemainder = (r * value) % k;
                    next[newRemainder] += dp[r];
                }
            }

            // add all subarrays ending at this index
            for (int r = 0; r < k; r++)
                answer[r] += next[r];
            
            dp = next;
        }

        return answer;
    }
}