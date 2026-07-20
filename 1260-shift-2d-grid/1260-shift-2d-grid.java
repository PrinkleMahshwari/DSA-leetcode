import java.util.*;

class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;

        k %= total;

        // pre-size the outer list to exactly m, avoids ArrayList's default
        // internal array doubling/growth as rows get added one by one
        List<List<Integer>> result = new ArrayList<>(m);

        int srcFlat = (total - k) % total;

        for (int i = 0; i < m; i++) {
            // exact capacity n avoids any internal resize inside each row's
            // backing array (ArrayList default growth would otherwise start
            // at 10 and grow by 1.5x, wasting allocated-but-unused slots)
            List<Integer> row = new ArrayList<>(n);

            for (int j = 0; j < n; j++) {
                int srcRow = srcFlat / n;
                int srcCol = srcFlat % n;

                // Integer boxing here is unavoidable given the List<List<Integer>>
                // return type mandated by the problem signature -- values in
                // range [-1000,1000] mean values -128..127 reuse Java's cached
                // Integer pool automatically, but outside that range each is
                // a genuinely new heap object regardless of what we do here
                row.add(grid[srcRow][srcCol]);

                srcFlat++;
                if (srcFlat == total) srcFlat = 0;
            }

            result.add(row);
        }

        return result;
    }
}