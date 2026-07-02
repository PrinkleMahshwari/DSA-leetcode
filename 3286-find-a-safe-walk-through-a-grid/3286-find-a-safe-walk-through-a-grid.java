import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

class Solution {
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();

        // 0-1 BFS (Deq-Based  Dijkstra Optimization)
        // Find the path that minimum health cost (damage taken)
        // since cost increments are strictly binary (either 1 or 0):
        //  cost = 0 (safe cell) --> push to FRONT of deque (process immediately)
        //  cost = 1 (unsafe cell) --> push to BACK of deque
        // this eliminates Log(N) PriorityQueue overhead, rendering true O(M * N) speed

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // track the minimum health cost (damage) taken to reach cell
        int[][] minDamage = new int[m][n];
        for (int i = 0; i < m; i++)
            Arrays.fill(minDamage[i], Integer.MAX_VALUE);
        
        // initialize at starting cell (0, 0)
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offerFirst(new int[]{0, 0});
        minDamage[0][0] = grid.get(0).get(0);

        while (!deque.isEmpty()) {
            int[] curr = deque.pollFirst();
            int r = curr[0];
            int c = curr[1];
            int currentDamage = minDamage[r][c];

            // early termination: if we reach the finish line, stop immediately
            if (r == m - 1 && c == n - 1)
                break;
            
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    int edgeWeight = grid.get(nr).get(nc);
                    int nextDamage = currentDamage + edgeWeight;

                    // if we found a strictly safer path to this neighboring cell
                    if (nextDamage < minDamage[nr][nc]) {
                        minDamage[nr][nc] = nextDamage;

                        // 0-1 BFS trick: avoid sorting completely
                        if (edgeWeight == 0)
                            deque.offerFirst(new int[]{nr, nc}); // highest priority path
                        
                        else 
                            deque.offerLast(new int[]{nr, nc}); // lower priority path
                    }
                }
            }
        }

        // check if total damage taken lets us survive
        int finalDamage = minDamage[m - 1][n - 1];
        return health - finalDamage >= 1;
    }
}