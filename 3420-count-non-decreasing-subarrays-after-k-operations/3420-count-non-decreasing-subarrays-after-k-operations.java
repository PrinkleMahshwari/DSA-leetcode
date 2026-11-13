class Solution {
    private long getCost (int left, int right, int[] nums, long[] prefix, int[][] jump, long[][] jumpSum) {
        long maxSum = 0;
        int current = left;

        // jump through complete prefix-maximum group
        for (int j = jump.length - 1; j >= 0; j--) {
            int next = jump[j][current];
            if (next != -1 && next <= right) {
                maxSum += jumpSum[j][current];
                current = next;
            }
        }

        // add the contribution of final incomplete group
        maxSum += (long) nums[current] * (right - current + 1);

        // minimum increments = prefix maximum sum - original sum
        Long originalSum = prefix[right + 1] - prefix[left];
        return maxSum - originalSum;
    }

    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;

        // step 1: build prefix sum
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];

        // step 2: find the next strictly greater elements
        int[] nextGreater = new int[n];
        int[] stack = new int[n];
        int top = -1;
        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) top--;
            nextGreater[i] = (top == -1) ? -1 : stack[top];
            stack[++top] = i;
        }    

        // step 3: build the binary lifting table
        int LOG = 32 - Integer.numberOfLeadingZeros (n);
        int[][] jump = new int[LOG][n];
        long[][] jumpSum = new long[LOG][n];
        for (int j = 0; j < LOG; j++) Arrays.fill (jump[j], -1);

        // one next-greater jump
        for (int i = 0; i < n; i++)
            if (nextGreater[i] != -1) {
                jump[0][i] = nextGreater[i];
                jumpSum[0][i] = (long) nums[i] * (nextGreater[i] - i);
            }

        // build powers of two
        for (int j = 1; j < LOG; j++) {
            for (int  i = 0; i < n; i++) {
                int mid = jump[j - 1][i];
                if (mid != -1 && jump[j - 1][mid] != -1) {
                    jump[j][i] = jump[j - 1][mid];
                    jumpSum[j][i] = jumpSum[j - 1][i] + jumpSum[j - 1][mid];
                }
            }
        }

        // step 4: sliding window
        long ans = 0;
        int right = 0;
        for (int left = 0; left < n; left++) {
            if (right < left) right = left;
            while (right + 1 < n && getCost (left, right + 1, nums, prefix, jump, jumpSum) <= k) right++;
            ans += right - left + 1;
        }

        return ans;
    }
}