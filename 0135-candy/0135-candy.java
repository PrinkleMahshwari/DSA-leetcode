import java.util.Arrays;

class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        if (n <= 1) return n;
        
        int[] candies = new int[n];
        // Requirement 1: Each child gets at least 1 candy
        Arrays.fill(candies, 1);
        
        // Pass 1: Handle left-to-right dependencies
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        
        // Pass 2: Handle right-to-left dependencies while preserving Pass 1 values
        int totalCandies = candies[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
            totalCandies += candies[i];
        }
        
        return totalCandies;
    }
}
