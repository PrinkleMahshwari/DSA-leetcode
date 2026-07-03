import java.util.*;

class Solution {

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        int m = edges.length;

        // forward star adjacency list, avoids List<List<>> allocation
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

        // sort a clone for binary search candidates, dedupe in place
        int[] uniqueCosts = cost.clone();
        Arrays.sort(uniqueCosts);
        int uniqueCount = 0;
        for (int i = 0; i < m; i++) {
            if (i == 0 || uniqueCosts[i] != uniqueCosts[i - 1]) {
                uniqueCosts[uniqueCount++] = uniqueCosts[i];
            }
        }

        // array based topo sort, no queue object overhead
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

            for (int e = head[node]; e != -1; e = next[e]) {
                int neighbor = to[e];
                if (--indegree[neighbor] == 0) {
                    queue[tailPtr++] = neighbor;
                }
            }
        }

        int targetTopoLimit = nodeToTopoIdx[n - 1];

        // single dp allocation reused across all binary search calls
        long[] dp = new long[n];
        // version stamp array, avoids Arrays.fill(dp, INF) every iteration
        int[] dpVersion = new int[n];
        int[] versionHolder = new int[1]; // mutable counter passed by reference

        int left = 0;
        int right = uniqueCount - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int candidateScore = uniqueCosts[mid];

            if (canReach(candidateScore, head, next, to, cost, topo, targetTopoLimit,
                    nodeToTopoIdx, online, k, n, dp, dpVersion, versionHolder)) {
                answer = candidateScore;
                left = mid + 1;
            } else {
                right = mid - 1;
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
        int[] dpVersion,
        int[] versionHolder) {

        long INF = 1L << 60;
        // bump version instead of memset, stale dp[v] entries auto-invalidate
        int curVersion = ++versionHolder[0];

        dp[0] = 0;
        dpVersion[0] = curVersion;

        int maxVisitedTopoIdx = 0;

        for (int i = 0; i <= targetTopoLimit; i++) {
            if (i > maxVisitedTopoIdx) {
                break;
            }

            int u = topo[i];
            if (dpVersion[u] != curVersion) {
                continue; // never reached this iteration, skip like it was INF
            }
            long dpU = dp[u];

            for (int e = head[u]; e != -1; e = next[e]) {
                int v = to[e];
                int edgeCost = cost[e];

                if (edgeCost < minimumEdgeCost) {
                    continue;
                }

                if (v != n - 1 && !online[v]) {
                    continue;
                }

                long nextCost = dpU + edgeCost;
                // treat unstamped dp[v] as INF automatically
                if (dpVersion[v] != curVersion || nextCost < dp[v]) {
                    dp[v] = nextCost;
                    dpVersion[v] = curVersion;

                    int vTopoIdx = nodeToTopoIdx[v];
                    if (vTopoIdx > maxVisitedTopoIdx) {
                        maxVisitedTopoIdx = vTopoIdx;
                    }
                }
            }
        }

        return dpVersion[n - 1] == curVersion && dp[n - 1] <= k;
    }
}