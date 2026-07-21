class Solution {
    public int jump(int[] nums) {

        int n = nums.length;
        int jumps = 0;
        int currentEnd = 0;   // farthest index reachable using "jumps" jumps so far
        int farthest = 0;     // farthest index reachable if we take one more jump

        // greedy BFS-style level expansion: process index i, track the best
        // reach possible from anything seen up to i. once i hits the current
        // level's boundary, a jump is forced to extend into the next level
        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;

                // early exit: already reached or passed the last index,
                // no need to keep scanning further
                if (currentEnd >= n - 1) break;
            }
        }

        return jumps;
    }
}