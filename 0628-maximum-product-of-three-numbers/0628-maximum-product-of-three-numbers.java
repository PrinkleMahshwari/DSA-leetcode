class Solution {
    public int maximumProduct(int[] nums) {
        // Track the three largest values
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        
        // Track the two smallest values (to catch pairs of large negative numbers)
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        
        for (int n : nums) {
            // Update the three largest values
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }
            
            // Update the two smallest values
            if (n < min1) {
                min2 = min1;
                min1 = n;
            } else if (n < min2) {
                min2 = n;
            }
        }
        
        // The answer is the maximum of the 3 largest numbers OR 2 smallest negatives * largest positive
        return Math.max(max1 * max2 * max3, min1 * min2 * max1);
    }
}
