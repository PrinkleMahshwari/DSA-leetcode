class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // fill from the back: nums1's unused trailing space is exactly where
        // the largest merged elements will end up, so placing from the end
        // avoids ever overwriting an unread nums1 element (no shifting needed)
        int i = m - 1; // last real element in nums1
        int j = n - 1; // last real element in nums2
        int k = m + n - 1; // last slot in nums1, where the next integer value goes

        while (j >= 0) {
            // once nums1's pointer is exhausted, everything remaining in
            // nums2 is already in correct sorted position, just copy it down
            if (i >= 0 && nums1[i] > nums2[j])
                nums1[k--] = nums1[i--];
            
            else
                nums1[k--] = nums2[j--];
        }
    }
}