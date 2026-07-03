import java.util.*;

class Solution {

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        int m = edges.length;

        // Optimization: Forward Star Adjacency List Structure using Primitive Arrays 
        // Eliminates List<List<Object>> allocation and reduces cache misses drastically.
        int[] head = new int[n];
        Arrays.fill(head, -1);
        int[] next = new int[m];
        int[] to = new int[m];
        int[] cost = new int[m];
        int[] indegree = new int[n];

        // Collect distinct edge costs for binary search
        int[] uniqueCosts = new int[m];

        for (int i = 0; i < m; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int c = edges[i][2];

            to[i] = v;
            cost[i] = c;
            next[i] = head[u];
            head[u] = i;

            indegree[v]++;
            uniqueCosts[i] = c;
        }

        // Optimization: Blazing Fast 16-bit Radix Sort instead of Arrays.sort()
        // Sorts edge costs in O(M) time, eliminating the O(M log M) quicksort overhead.
        int[] tempCosts = new int[m];
        int[] count = new int[65536];

        // Pass 1: Lower 16 bits
        for (int i = 0; i < m; i++) count[uniqueCosts[i] & 0xFFFF]++;
        for (int i = 1; i < 65536; i++) count[i] += count[i - 1];
        for (int i = m - 1; i >= 0; i--) tempCosts[--count[uniqueCosts[i] & 0xFFFF]] = uniqueCosts[i];

        // Pass 2: Upper 16 bits
        Arrays.fill(count, 0);
        for (int i = 0; i < m; i++) count[(tempCosts[i] >>> 16) & 0xFFFF]++;
        for (int i = 1; i < 65536; i++) count[i] += count[i - 1];
        for (int i = m - 1; i >= 0; i--) uniqueCosts[--count[(tempCosts[i] >>> 16) & 0xFFFF]] = tempCosts[i];

        // Optimization: High-Speed Deduplication Without TreeSet
        // Tracking unique costs via pointer adjustments is much faster.
        int uniqueCount = 0;
        for (int i = 0; i < m; i++) {
            if (i == 0 || uniqueCosts[i] != uniqueCosts[i - 1]) {
                uniqueCosts[uniqueCount++] = uniqueCosts[i];
            }
        }

        // Optimization: Stack/Array-Based Fast Topological Sort Queue 
        int[] topo = new int[n];
        int[] queue = new int[n];
        int headPtr = 0, tailPtr = 0;

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue[tailPtr++] = i;
            }
        }

        int topoIdx = 0;
        int targetTopoLimit = n; // Optimization: Find where node n-1 sits to prune DP loops completely

        while (headPtr < tailPtr) {
            int node = queue[headPtr++];
            topo[topoIdx++] = node;

            for (int e = head[node]; e != -1; e = next[e]) {
                int neighbor = to[e];
                if (--indegree[neighbor] == 0) {
                    queue[tailPtr++] = neighbor;
                }
            }
        }

        // Optimization: Track the exact index of n-1 in topo to safely skip downstream nodes
        for (int i = 0; i < n; i++) {
            if (topo[i] == n - 1) {
                targetTopoLimit = i;
                break;
            }
        }

        // Optimization: Single Memory Allocation for DP State & Reset Tracking array
        long[] dp = new long[n];
        long INF = 1L << 60;
        Arrays.fill(dp, INF); 
        int[] resetQueue = new int[n];

        // Step 2: Binary search on answer
        int left = 0;
        int right = uniqueCount - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int candidateScore = uniqueCosts[mid];

            // Verify if a valid path exists with minimum edge cost >= candidateScore
            if (canReach(candidateScore, head, next, to, cost, topo, targetTopoLimit, online, k, n, dp, resetQueue, INF)) {
                answer = candidateScore;
                left = mid + 1; // Try to maximize the bottleneck cost
            } else {
                right = mid - 1; // Candidate score is too large
            }
        }

        return answer;
    }

    // DP over Topological Ordering using primitive forward-star graph parameters
    private boolean canReach(
        int minimumEdgeCost,
        int[] head,
        int[] next,
        int[] to,
        int[] cost,
        int[] topo,
        int targetTopoLimit,
        boolean[] online,
        long k,
        int n,
        long[] dp,
        int[] resetQueue,
        long INF) {

        // Optimization: Track and reset ONLY modified nodes from the previous round instead of O(N) Arrays.fill
        int resetCount = 0;
        dp[0] = 0;
        resetQueue[resetCount++] = 0;

        // Optimization: Loop only runs up to targetTopoLimit because elements after n-1 in a DAG can't reach it
        for (int i = 0; i <= targetTopoLimit; i++) {
            int u = topo[i];
            long dpU = dp[u];

            if (dpU == INF) {
                continue;
            }
                
            // Fast linear iteration over primitive edges
            for (int e = head[u]; e != -1; e = next[e]) {
                int v = to[e];
                int edgeCost = cost[e];

                // Ignore edges violating the bottleneck constraint
                if (edgeCost < minimumEdgeCost) {
                    continue;
                }
                    
                // Intermediate nodes must be online
                if (v != n - 1 && !online[v]) {
                    continue;
                }
                    
                // Fast path relaxation state change
                long nextCost = dpU + edgeCost;
                if (nextCost < dp[v]) {
                    if (dp[v] == INF) {
                        resetQueue[resetCount++] = v; // Log index to safely clean up later
                    }
                    dp[v] = nextCost;
                }
            }
        }

        boolean result = dp[n - 1] <= k;

        // Clean up memory state only for visited indexes to protect the next binary search iteration
        for (int i = 0; i < resetCount; i++) {
            dp[resetQueue[i]] = INF;
        }

        return result;
    }
}
