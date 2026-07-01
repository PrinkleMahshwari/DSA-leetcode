import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

class Solution {

    // Directions for moving up, down , left, right
    private final int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();

        // Gaurd check: if start or end is a thief, safety is instantly 0
        if (grid.get(0).get(0) == 1 || grid.get(n - 1).get(n - 1) == 1) 
            return 0;

        // Step 1: Mutli-Source BFS
        // Find the distance to the neares thief for every cell
        Queue<int[]> thiefQueue = new ArrayDeque<>();
        int[][] dist = new int[n][n];

        // initialize distance matrix with infinity (-1 helps us see unvisited cells) 
        for (int i = 0; i < n; i++)
            Arrays.fill(dist[i], Integer.MAX_VALUE);

        // Shove every single thief into the queue at once with distance 0
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid.get(r).get(c) == 1) {
                    thiefQueue.offer(new int[]{r, c});
                    dist[r][c] = 0;
                }
            }
        }

        // expand layer outwardly simultaneously
        while (!thiefQueue.isEmpty()) {
            int[] curr = thiefQueue.poll();
            int r = curr[0];
            int c = curr[1];

            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                // if neighbor is valid and we found a shorter path to a thief
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && dist[nr][nc] == Integer.MAX_VALUE) {
                    dist[nr][nc] = dist[r][c] + 1;
                    thiefQueue.offer(new int[]{nr, nc});
                }
            }
        }

        // Step 3 : Binary Search on Answer
        // Find the maximum targetSafeness that yiedls a "Yes" (True)

        // max possible safeness is capped by the starting point's distance
        int low = 0;
        int high = dist[0][0];
        int bestSafeness = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            // if "Yes, safeness >= mid works" --> look for an even better anwer
            if (canReachEnd(dist, mid, n)) {
                bestSafeness = mid;
                low = mid + 1;
            }
            // if "No" --> target was too high, restrict search to smaller limits
            else {
                high = mid - 1;
            }
        }
        return bestSafeness;
    }

    // Step 2: The feasibility check (BFS)
    // can we reach (n-1, n-1) using only cells with safeness >= targetSafeness
    private boolean canReachEnd(int[][] dist, int targetSafeness, int n) {

        // if start or end cell itself breaks the rule, instantly impossile
        if (dist[0][0] < targetSafeness || dist[n - 1][n - 1] < targetSafeness) 
            return false;
        
        Queue<int[]> pathQueue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];

        pathQueue.offer(new int[] {0, 0});
        visited[0][0]= true;

        while (!pathQueue.isEmpty()) {
            int[] curr = pathQueue.poll();
            int r = curr[0];
            int c = curr[1];

            if (r == n - 1 && c == n - 1) 
                return true; // Reached the finish line safely
            
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n && !visited[nr][nc]) {
                    // ignore cells smaller than targetSafeness (Treat as X)
                    if (dist[nr][nc] >= targetSafeness) {
                        visited[nr][nc] = true;
                        pathQueue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        
        return false;
    }
}