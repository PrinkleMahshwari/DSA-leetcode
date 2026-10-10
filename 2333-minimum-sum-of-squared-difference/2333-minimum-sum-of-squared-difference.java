class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];

        int maxDiff = 0;
        long totalDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            totalDiff += diff[i];
        }

        long k = (long) k1 + k2;

        // If all differences can become zero
        if (k >= totalDiff) return 0;

        // Binary search for the smallest possible max difference
        int low = 0;
        int high = maxDiff;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long needed = 0;

            for (int d : diff) 
                if (d > mid) needed += d - mid;

            if (needed <= k) high = mid;
            else low = mid + 1;
        }

        int limit = low;
        long needed = 0;
        long result = 0;

        for (int d : diff) {
            int reduced = Math.min(d, limit);

            needed += d - reduced;
            result += (long) reduced * reduced;
        }

        // Use remaining operations to reduce some differences by one more
        long remaining = k - needed;
        result -= remaining * (2L * limit - 1);

        return result;
    }
}