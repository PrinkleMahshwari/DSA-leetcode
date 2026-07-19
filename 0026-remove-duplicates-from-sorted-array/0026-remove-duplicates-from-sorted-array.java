class Solution {
    public int removeDuplicates(int[] nums) {
        
        int n = nums.length;
        if (n == 0) return 0;

        // slow pointer marks the boundary of unique elements placed so far,
        // fast pointer scans ahead looking for the next distinct value.
        // sorted input guarantees duplicates are always adjacent, so a
        // simple "compare with last kept value" check is enough
        int slow = 0;
        
        for (int fast = 1; fast < n; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
            // if equal skip -- this duplicate is simply left behind
        }

        return slow + 1;
    }
}