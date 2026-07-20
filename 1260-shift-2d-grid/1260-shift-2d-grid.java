class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;

        // effective shift, avoids doing fulll redundant rotations when k > total
        k %= total;

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < m; i++)
            result.add(new ArrayList<>(Collections.nCopies(n, 0)));
        
        // treat the grid as one flattened sequence of length m*n: element at
        // flat index p moves to flat index (p + k) % total. rather than
        // simulating k shifts one at a time, compute each element's final
        // position directly in a single pass -- O(m*n) regardless of k
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int flatIndex = i * n + j;
                int newFlatIndex = (flatIndex + k) % total;

                int newRow = newFlatIndex/n;
                int newCol = newFlatIndex % n;

                result.get(newRow).set(newCol, grid[i][j]);
            }
        }

        return result;
    }
}