class Solution {
    public int maxArea(int[] height) {
        
        // two pointers
        int left = 0;
        int right = height.length - 1;

        // store maximum area
        int maxArea = 0;

        // loop until pointers meet
        while (left < right) {

            // calculate width
            int width = right - left;

            // calculate height (limited by smaller line)
            int h = Math.min(height[left], height[right]);

            // curren area
            int area = width * h;

            // update maximum
            maxArea = Math.max(maxArea, area);

            // move pointer with smaller height
            if (height[left] < height[right]) {
                left++; // try to find a bigger left boundary
            } else {
                right--; // try to find a bigger right boundary
            }
        }
        return maxArea;
    }
}