import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();

        // Gaurd check: if start or end is a thief, safety is instantly 0
        if (grid.get(0).get(0) == 1 || grid.get(n - 1).get(n - 1) == 1) 
            return 0;
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Step 1: Mutli-Source BFS
        // Find the distance to the nearest thief for every cell
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

        // Step 2: Modified Dijkstra (Max-Priority Queue)
        // each entry in heap: {safeness_so_far, row, col}
        // sorted descending by safeness_so_far to always pick the safest option first
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));

        // track the maximum safeness achieved to reach each node
        int[][] maxSafenessToNode = new int[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(maxSafenessToNode[i], -1);
        
        // initialize at start cell (0, 0)
        maxHeap.offer(new int[]{dist[0][0], 0, 0});
        maxSafenessToNode[0][0] = dist[0][0];

        while (!maxHeap.isEmpty()) {
            int[] curr = maxHeap.poll();
            int currentSafeness = curr[0];
            int r = curr[1], c = curr[2];

            // Critical speedup: if we reached the end, this path is guaranteed optimal
            if (r == n - 1 && c == n - 1)
                return currentSafeness;
            
            // skip if we already found a path to this node with a better safeness factor
            if (currentSafeness < maxSafenessToNode[r][c])
                continue;
            
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];

                if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                    // the safeness of the path moving into the neighbor is limited
                    // by the minimum value seen along the way
                    int nextSafeness = Math.min(currentSafeness, dist[nr][nc]);

                    // if this new path offers a better safeness factor than previousy recorded
                    if (nextSafeness > maxSafenessToNode[nr][nc]) {
                        maxSafenessToNode[nr][nc] = nextSafeness;
                        maxHeap.offer(new int[]{nextSafeness, nr, nc});
                    }
                }
            }
        }

        return 0;
    }
}