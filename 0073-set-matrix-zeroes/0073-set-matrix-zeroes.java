class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean firstColZero = false;
        
        // Step 1: Scan the matrix and use the first row & column as tracking flags
        for (int r = 0; r < m; r++) {
            // Check if the first column contains a zero
            if (matrix[r][0] == 0) {
                firstColZero = true;
            }
            
            // Check the rest of the cells in the row
            for (int c = 1; c < n; c++) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0; // Set row marker
                    matrix[0][c] = 0; // Set column marker
                }
            }
        }
        
        // Step 2: Use the markers to set inner cells to zero
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) {
                    matrix[r][c] = 0;
                }
            }
        }
        
        // Step 3: Handle the first row itself
        if (matrix[0][0] == 0) {
            for (int c = 0; c < n; c++) {
                matrix[0][c] = 0;
            }
        }
        
        // Step 4: Handle the first column itself
        if (firstColZero) {
            for (int r = 0; r < m; r++) {
                matrix[r][0] = 0;
            }
        }
    }
}
