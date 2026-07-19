class Solution {
    public int removeElement(int[] nums, int val) {
        
        int n = nums.length;
        int end = n; // exclusive boundary of the "unknown" region at the back

        int i = 0;

        // swap-with-end approach: since order doesn't matter, when we hit a
        // val, instead of shifting everything left (costly for many matches),
        // just pull in the last unexamined element from the back. This does
        // fewer total writes when val is rare, and never more than a plain
        // left-compaction would in the worst case
        while (i < end) {
            if (nums[i] == val) {
                end--;
                nums[i] = nums[end]; // pull last unknown element forward
                // don't advance i her -- the newly swapped-in value at i
                // hasn't been checked yet, it might also equal val
            } else {
                i++;
            }
        }

        return end;
    }
}