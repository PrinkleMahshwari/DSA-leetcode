import java.util.*;

class Solution {

    public int candy(int[] ratings) {

        int n = ratings.length;

        if (n <= 1)
            return n;

        int[] candies = new int[n];

        // first child always gets one candy
        candies[0] = 1;

        // pass 1: satisfy the left neighbor constraint
        for (int i = 1; i < n; i++) {

            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            } else {
                // every child must receive at least one candy
                candies[i] = 1;
            }
        }

        // initialize answer with the last child's candies
        int totalCandies = candies[n - 1];

        // pass 2: satisfy the right neighbor constraint while preserving
        // any larger value assigned during the first pass
        for (int i = n - 2; i >= 0; i--) {

            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(
                    candies[i],
                    candies[i + 1] + 1
                );
            }

            totalCandies += candies[i];
        }

        return totalCandies;
    }
}