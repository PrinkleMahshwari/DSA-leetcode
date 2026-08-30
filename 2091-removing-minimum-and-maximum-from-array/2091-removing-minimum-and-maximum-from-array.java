class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIndex = 0;
        int maxIndex = 0;

        // find min and max indices 
        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[minIndex])
                minIndex = i;
            
            if (nums[i] > nums[maxIndex])
                maxIndex = i;
        }

        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        // remove both from the front
        int fromFront = right + 1;

        // remove both from the back
        int fromBack = n - left;

        // remove one from front, and one from back
        int fromBoth = (left + 1) + (n - right);

        return Math.min(fromFront, Math.min(fromBack, fromBoth));
    }
}