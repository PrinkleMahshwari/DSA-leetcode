import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0;
        int startC = 0;

        int litterCount = 0;

        // assign an index to every litter cell
        int[][] litterId = new int[m][n];

        for (int[] row : litterId)
            Arrays.fill(row, -1);
        
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {

                char ch = classroom[r].charAt(c);

                if (ch == 'S') {
                    startR = r;
                    startC = c;
                } else if (ch == 'L') {
                    litterId[r][c] = litterCount++;
                }
            }
        }

        // already clean
        if (litterCount == 0)
            return 0;

        int fullMask = (1 << litterCount) - 1;

        int[][][] bestEnergy = new int[m][n][1 << litterCount];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                Arrays.fill(bestEnergy[r][c], -1);
            }
        }

        Queue<State> queue = new ArrayDeque<>();

        queue.offer(new State(startR, startC, 0, energy, 0));

        bestEnergy[startR][startC][0] = energy;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            State current = queue.poll();

            int r = current.r;
            int c = current.c;
            int mask = current.mask;
            int e = current.energy;
            int moves = current.moves;

            if (mask == fullMask)
                return moves;
            
            for (int d = 0; d < 4; d++) {

                int nr = r + dr[d];
                int nc = c + dc[d];

                // outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n)
                    continue;
                
                // obstacle
                if (classroom[nr].charAt(nc) == 'X')
                    continue;
                
                // no energy to make this move
                if (e == 0)
                    continue;
                
                int newEnergy = e - 1;
                int newMask = mask;

                char cell = classroom[nr].charAt(nc);

                // collect litter
                if (cell == 'L') {
                    int id = litterId[nr][nc];
                    newMask |= (1 << id);
                }

                // reset energy
                if (cell == 'R')
                    newEnergy = energy;
                
                if (newEnergy <= bestEnergy[nr][nc][newMask])
                    continue;
                
                bestEnergy[nr][nc][newMask] = newEnergy;

                queue.offer(new State(nr, nc, newMask, newEnergy, moves + 1));
            }
        }

        return -1;
    }

    private static class State {
        int r;
        int c;
        int mask;
        int energy;
        int moves;

        State(int r, int c, int mask, int energy, int moves) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.moves = moves;
        }
    }
}