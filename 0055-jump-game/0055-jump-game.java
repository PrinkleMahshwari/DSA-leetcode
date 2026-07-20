class Solution {
    public boolean canJump(int[] nums) {
        
        int n = nums.length;

        // store the farthest index we can currently reach
        int farthestReachable = 0;

        for (int i = 0; i < n; i++) {

            // current index is outside our reachable range
            // meaning there is no possible jump path to this index
            if (i > farthestReachable)
                return false;
            
            // update the maximum distance we can reach from current position
            farthestReachable = Math.max(farthestReachable, i + nums[i]);
        }

        // if we never got stuck, the last index is reachable
        return true;
    }
}