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

        // Optimization: Rely on high-performance native JVM sorting
        Arrays.sort(uniqueCosts);
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

        // Track the topological index for each node for instant O(1) lookups
        int[] nodeToTopoIdx = new int[n];

        while (headPtr < tailPtr) {
            int node = queue[headPtr++];
            nodeToTopoIdx[node] = topoIdx;
            topo[topoIdx++] = node;

            for (int e = head[node]; e != -1; e = next[e]) {
                int neighbor = to[e];
                if (--indegree[neighbor] == 0) {
                    queue[tailPtr++] = neighbor;
                }
            }
        }

        // Optimization: Track the exact index of n-1 in topo to safely skip downstream nodes
        targetTopoLimit = nodeToTopoIdx[n - 1];

        // Optimization: Single Memory Allocation for DP State
        // Pre-allocating the DP array here saves allocation overhead inside the loop.
        long[] dp = new long[n];

        // Step 2: Binary search on answer
        int left = 0;
        int right = uniqueCount - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int candidateScore = uniqueCosts[mid];

            // Verify if a valid path exists with minimum edge cost >= candidateScore
            if (canReach(candidateScore, head, next, to, cost, topo, targetTopoLimit, nodeToTopoIdx, online, k, n, dp)) {
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
        int[] nodeToTopoIdx,
        boolean[] online,
        long k,
        int n,
        long[] dp) {

        // Using a highly large boundary value instead of Long.MAX_VALUE to prevent potential overflow
        long INF = 1L << 60;
        Arrays.fill(dp, INF);
        dp[0] = 0;

        // Optimization: Track the maximum topological index currently reached to prune unreachable nodes early
        int maxVisitedTopoIdx = 0;

        // Process every node exactly once in topological order up to targetTopoLimit
        for (int i = 0; i <= targetTopoLimit; i++) {
            // If the loop pointer passes the maximum reachable topological index, we can safely terminate early
            if (i > maxVisitedTopoIdx) {
                break;
            }

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
                    dp[v] = nextCost;
                    
                    // Optimization: Dynamically expand the reachable topological search horizon
                    int vTopoIdx = nodeToTopoIdx[v];
                    if (vTopoIdx > maxVisitedTopoIdx) {
                        maxVisitedTopoIdx = vTopoIdx;
                    }
                }
            }
        }

        // Valid path exists only if the total accumulation stays within budget constraints
        return dp[n - 1] <= k;
    }
}
