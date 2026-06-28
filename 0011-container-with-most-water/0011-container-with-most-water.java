class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            // Cache current boundary heights
            int leftVal = height[left];
            int rightVal = height[right];

            // Condition 1: Left side is smaller
            if (leftVal < rightVal) {
                int area = leftVal * (right - left);
                if (area > maxArea) {
                    maxArea = area;
                }
                // Fast-forward left pointer past any shorter bars
                while (left < right && height[left] <= leftVal) {
                    left++;
                }
            } 
            // Condition 2: Right side is smaller or equal
            else {
                int area = rightVal * (right - left);
                if (area > maxArea) {
                    maxArea = area;
                }
                // Fast-forward right pointer past any shorter bars
                while (left < right && height[right] <= rightVal) {
                    right--;
                }
            }
        }
        return maxArea;
    }
}
