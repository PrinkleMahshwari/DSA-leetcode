class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        // case 1: only one subarray exist (k == n)
        if (k == n) {
            int max = -1;

            for (int num : nums)
                max = Math.max(max, num);
            
            return max;
        }

        int[] freq = new int[51];

        for (int num : nums)
            freq[num]++;
        
        // case 2: every subarray has size 1
        if (k == 1) {
            for (int x = 50; x >= 0; x--) {
                if (freq[x] == 1)
                    return x;
                
            }

            return -1;
        }

        // case 3: 1 < k < n
        int ans = -1;

        if (freq[nums[0]] == 1)
            ans = Math.max(ans, nums[0]);
        
        if (freq[nums[n - 1]] == 1)
            ans = Math.max(ans, nums[n - 1]);
        
        return ans;
    }
}