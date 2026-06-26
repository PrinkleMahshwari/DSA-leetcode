class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // ensures num1 is the smaller array 
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int low = 0;
        int high = m;
        int total = m + n;
        int totalLeft = (total + 1) / 2;

        while (low <= high) {
            int i = low + (high - low) / 2;
            int j = totalLeft - i;

            // handle edge cases where partitioning falls outisde array bound
            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];

            int left2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            // valid partition found
            if (left1 <= right2 && left2 <= right1) {
                // if total elements count is odd
                if (total % 2 != 0) {
                    return Math.max(left1, left2);
                }
                // if total elements count is even
                return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
            }
            // too far right in nums1, move left
            else if (left1 > right2) {
                high = i - 1;
            }
            // too far left in nums1, move right
            else {
                low = i + 1;
            }
        }
        return 0.0;
    }
}