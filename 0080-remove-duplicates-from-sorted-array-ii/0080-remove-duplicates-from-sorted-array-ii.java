class Solution {
    public int removeDuplicates(int[] nums) {
        
        int n = nums.length;

        // arrays of size 1 or 2 are already valid
        if (n <= 2) return n;

        // first two elements are always allowed
        int write = 2;

        // process the remaining elements
        for (int i = 2; i < n; i++) {

            // if adding nums[i] does not create three occurrences,
            // keep it in the valid portion of the array
            if (nums[i] != nums[write - 2]) {
                nums[write] = nums[i];
                write++;
            }
        }

        // write represents the length of modified array
        return write;
    }
}