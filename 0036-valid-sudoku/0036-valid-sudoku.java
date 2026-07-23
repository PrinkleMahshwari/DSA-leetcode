class Solution {
    public boolean isValidSudoku(char[][] board) {
        // Bitmask arrays to track digits 1-9 for rows, columns, and 3x3 boxes
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];
        
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                
                // Skip empty cells
                if (val == '.') {
                    continue;
                }
                
                // Convert character digit directly to numeric int value
                int digit = val - '0';
                int mask = 1 << digit;
                
                // Determine the 3x3 sub-box index (0 to 8)
                int boxIdx = (r / 3) * 3 + (c / 3);
                
                // Check if the digit bit is already flagged in row, column, or box
                if ((rows[r] & mask) != 0 || (cols[c] & mask) != 0 || (boxes[boxIdx] & mask) != 0) {
                    return false;
                }
                
                // Flag the digit bit as seen across the records
                rows[r] |= mask;
                cols[c] |= mask;
                boxes[boxIdx] |= mask;
            }
        }
        
        return true;
    }
}
