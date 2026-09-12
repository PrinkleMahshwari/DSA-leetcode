class Solution {

    static class State {
        long score;
        int[] ids;

        State (long score, int[] ids) {
            this.score = score;
            this.ids = ids;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        
        int n = intervals.size();

        // [left, right, weight, originalIndex]
        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        // sort by right endpoint
        Arrays.sort(a, (x, y) -> Integer.compare(x[1], y[1]));

        // prev[i] = last interval ending before a[i] starts
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {

            int lo = 0;
            int hi = i - 1;
            int pos = -1;

            while (lo <= hi) {

                int mid = lo + (hi - lo) / 2;

                if (a[mid][1] < a[i][0]) {
                    pos = mid;
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            prev[i] = pos;
        }

        State[][] dp = new State[5][n + 1];

        for (int i = 0; i <= n; i++)
            dp[0][i] = new State (0, new int[0]);
        
        for (int k = 1; k <= 4; k++)
            dp[k][0] = new State (0, new int[0]);
        
        for (int k = 1; k <= 4; k++) {

            for (int i = 1; i <= n; i++) {

                int cur = i - 1;

                State skip = dp[k][i - 1];

                State base = dp[k - 1][prev[cur] + 1];

                int[] ids = add(base.ids, a[cur][3]);

                State take = new State (base.score + a[cur][2], ids);
                dp[k][i] = better(skip, take);
            }
        }

        return dp[4][n].ids;
    }

    // add original index while keeping indices sorted
    private int[] add (int[] ids, int value) {

        int n = ids.length;
        int[] result = new int[n + 1];

        int i = 0;

        while (i < n && ids[i] < value) {
            result[i] = ids[i];
            i++;
        }

        result[i] = value;

        while (i < n) {
            result[i + 1] = ids[i];
            i++;
        }

        return result;
    }

    // higher score wins
    // if scores are equal, lexicographically smaller indices win
    private State better (State a, State b) {

        if (a.score != b.score)
            return a.score > b.score ? a : b;
        
        return compare(a.ids, b.ids) <= 0 ? a : b;
    }

    private int compare (int[] a, int[] b) {

        int n = Math.min(a.length, b.length);

        for (int i = 0; i < n; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }
}