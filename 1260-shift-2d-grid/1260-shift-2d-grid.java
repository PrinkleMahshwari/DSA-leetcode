class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;

        k %= total;

        // iterate DESTINATION cells in row-major order and pull the source
        // value for each: dest flat index d comes from source (d - k + total) % total.
        // this lets us just append() into each row in order, instead of the
        // previous version's random-access set() into a pre-sized nCopies list
        // (which allocates a full total-sized Integer(0) list upfront, then
        // does bounds-checked replacement writes out of order)
        List<List<Integer>> result = new ArrayList<>(m);

        int srcFlat = (total - k) % total; // source index for destination flat index 0

        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>(n);

            for (int j = 0; j < n; j++) {
                int srcRow = srcFlat / n;
                int srcCol = srcFlat % n;

                row.add(grid[srcRow][srcCol]);

                srcFlat++;
                if (srcFlat == total) srcFlat = 0; // avoid repeated % on every cell
            }

            result.add(row);
        }

        return result;
    }
}