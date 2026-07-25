class Solution {
    public int trap(int[] height) {
        if (height == null || height.length == 0) return 0;
        
        int left = 0;
        int right = height.length - 1;
        
        int leftMax = 0;
        int rightMax = 0;
        int waterTrapped = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                // The left side limits the water boundary height
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // Update left boundary
                } else {
                    waterTrapped += leftMax - height[left]; // Accumulate water units
                }
                left++;
            } else {
                // The right side limits the water boundary height
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // Update right boundary
                } else {
                    waterTrapped += rightMax - height[right]; // Accumulate water units
                }
                right--;
            }
        }
        
        return waterTrapped;
    }
}
