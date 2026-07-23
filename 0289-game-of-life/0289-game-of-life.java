class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        
        // Direction offsets to scan all 8 surrounding neighbors
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        // Step 1: Track simultaneously modified state changes using state transitions
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                int liveNeighbors = 0;
                
                // Count active neighbors
                for (int i = 0; i < 8; i++) {
                    int nr = r + dr[i];
                    int nc = c + dc[i];
                    
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                        // States 1 and 2 mean the cell is/was alive in the current generation
                        if (board[nr][nc] == 1 || board[nr][nc] == 2) {
                            liveNeighbors++;
                        }
                    }
                }
                
                // Apply rules for Live Cells
                if (board[r][c] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        board[r][c] = 2; // Transition: Alive to Dead
                    }
                } 
                // Apply rules for Dead Cells
                else if (board[r][c] == 0) {
                    if (liveNeighbors == 3) {
                        board[r][c] = 3; // Transition: Dead to Alive
                    }
                }
            }
        }
        
        // Step 2: Resolve transition placeholders back to standard 0 and 1 values
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (board[r][c] == 2) {
                    board[r][c] = 0;
                } else if (board[r][c] == 3) {
                    board[r][c] = 1;
                }
            }
        }
    }
}
