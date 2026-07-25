class Solution {

    public int candy(int[] ratings) {

        int n = ratings.length;

        if (n <= 1)
            return n;

        int totalCandies = 1;

        int up = 0;      // length of current increasing slope
        int down = 0;    // length of current decreasing slope
        int peak = 0;    // length of the last increasing slope

        for (int i = 1; i < n; i++) {

            // increasing slope
            if (ratings[i] > ratings[i - 1]) {
                up++;
                peak = up;
                down = 0;

                totalCandies += (up + 1);
            }

            // flat slope
            else if (ratings[i] == ratings[i - 1]) {
                up = 0;
                down = 0;
                peak = 0;

                totalCandies += 1;
            }

            // decreasing slope
            else {
                up = 0;
                down++;

                totalCandies += down;

                // if decreasing length exceeds previous peak,
                // peak child needs one extra candy
                if (down > peak)
                    totalCandies++;
            }
        }

        return totalCandies;
    }
}