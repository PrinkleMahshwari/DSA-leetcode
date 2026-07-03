import java.util.*;

class Solution {

    // instance field replaces int[1] wrapper array, one less heap allocation per call
    private int versionCounter = 0;

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        int m = edges.length;

        // forward star adjacency list: three parallel arrays instead of List<List<>>
        // avoids boxing and gives sequential memory access during traversal
        int[] head = new int[n];
        Arrays.fill(head, -1);
        int[] next = new int[m];
        int[] to = new int[m];
        int[] cost = new int[m];
        int[] indegree = new int[n];

        for (int i = 0; i < m; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int c = edges[i][2];

            to[i] = v;
            cost[i] = c;
            next[i] = head[u];
            head[u] = i;

            indegree[v]++;
        }

        // clone costs for sorting so original edge order stays intact for the adjacency list
        int[] uniqueCosts = cost.clone();
        Arrays.sort(uniqueCosts);
        int uniqueCount = 0;
        for (int i = 0; i < m; i++) {
            if (i == 0 || uniqueCosts[i] != uniqueCosts[i - 1]) {
                uniqueCosts[uniqueCount++] = uniqueCosts[i];
            }
        }

        // Kahn's algorithm using plain int arrays as the queue, no ArrayDeque object overhead
        int[] topo = new int[n];
        int[] queue = new int[n];
        int headPtr = 0, tailPtr = 0;

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue[tailPtr++] = i;
            }
        }

        int topoIdx = 0;
        int[] nodeToTopoIdx = new int[n];

        while (headPtr < tailPtr) {
            int node = queue[headPtr++];
            nodeToTopoIdx[node] = topoIdx;
            topo[topoIdx++] = node;

            for (int edge = head[node]; edge != -1; edge = next[edge]) {
                int neighbor = to[edge];
                if (--indegree[neighbor] == 0) {
                    queue[tailPtr++] = neighbor;
                }
            }
        }

        // node n-1 is the only target we care about, no need to process topo order past it
        int targetTopoLimit = nodeToTopoIdx[n - 1];

        // dp and dpVersion are allocated once and reused across every binary search call,
        // instead of allocating a fresh long[] each time canReach() runs
        long[] dp = new long[n];
        int[] dpVersion = new int[n];

        int left = 0;
        int right = uniqueCount - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int candidateScore = uniqueCosts[mid];

            if (canReach(candidateScore, head, next, to, cost, topo, targetTopoLimit,
                    nodeToTopoIdx, online, k, n, dp, dpVersion)) {
                answer = candidateScore;
                left = mid + 1; // this bottleneck works, try a stricter (higher) one
            } else {
                right = mid - 1; // too strict, relax the bottleneck
            }
        }

        return answer;
    }

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
        long[] dp,
        int[] dpVersion) {

        // Version stamping instead of Arrays.fill(dp, INF):
        // Arrays.fill is O(n) and runs on every one of the ~log(m) binary search calls,
        // even though only a handful of nodes actually get touched each round.
        // Bumping a version counter and stamping dpVersion[node] = curVersion on write
        // makes "is this dp value valid right now" an O(1) check instead of a reset pass.
        int curVersion = ++versionCounter;

        dp[0] = 0;
        dpVersion[0] = curVersion;

        // tracks the furthest topo index touched so far, lets us break out of the loop
        // as soon as there's nothing left reachable, instead of scanning to targetTopoLimit
        int maxVisitedTopoIdx = 0;

        for (int i = 0; i <= targetTopoLimit; i++) {
            if (i > maxVisitedTopoIdx) {
                break; // everything beyond this point is unreachable this round
            }

            int u = topo[i];
            if (dpVersion[u] != curVersion) {
                continue; // stale stamp means this node was never reached, treat as INF
            }
            long currentCost = dp[u];

            for (int edge = head[u]; edge != -1; edge = next[edge]) {
                int edgeCost = cost[edge];
                if (edgeCost < minimumEdgeCost) {
                    continue; // check cost before touching "to[]" to skip a wasted array read
                }

                int v = to[edge];
                if (v != n - 1 && !online[v]) {
                    continue; // intermediate nodes must be online, final node is exempt
                }

                long nextCost = currentCost + edgeCost;
                // dpVersion[v] != curVersion means dp[v] is stale, treat it as INF automatically
                if (dpVersion[v] != curVersion || nextCost < dp[v]) {
                    dp[v] = nextCost;
                    dpVersion[v] = curVersion;

                    int vTopoIdx = nodeToTopoIdx[v];
                    if (vTopoIdx > maxVisitedTopoIdx) {
                        maxVisitedTopoIdx = vTopoIdx; // expand the search horizon
                    }
                }
            }
        }

        return dpVersion[n - 1] == curVersion && dp[n - 1] <= k;
    }
}