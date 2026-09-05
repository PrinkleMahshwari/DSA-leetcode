class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;

        int[] prefixMax = new int[n];
        int[] suffMin = new int[n];

        // prefix maximum
        prefixMax[0] = nums[0];

        for (int i = 1; i < n; i++)
            prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
        
        // suffix minimum
        suffMin[n - 1] = nums[n - 1];
        
        for (int i = n - 2; i >= 0; i--)
            suffMin[i] = Math.min(suffMin[i + 1], nums[i]);
        
        // smallest stable index
        for (int i = 0; i < n; i++)
            if (prefixMax[i] - suffMin[i] <= k)
                return i;
        
        return -1;
    }
}