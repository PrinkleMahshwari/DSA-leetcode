import java.util.*;

class Solution {

    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        // sort indices by nums value, since edges depend only on value gap
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> nums[a] - nums[b]);

        int[] sortedVal = new int[n];
        int[] posInSorted = new int[n]; // original index -> sorted position
        for (int i = 0; i < n; i++) {
            sortedVal[i] = nums[order[i]];
            posInSorted[order[i]] = i;
        }

        // farthest[i] = furthest sorted position reachable in ONE hop from i
        // two pointer works since sortedVal is non-decreasing
        int[] farthest = new int[n];
        int right = 0;
        for (int i = 0; i < n; i++) {
            if (right < i) right = i;
            while (right + 1 < n && sortedVal[right + 1] - sortedVal[i] <= maxDiff) right++;
            farthest[i] = right;
        }

        // component id via interval-merge sweep, same idea as part I but generalized
        int[] component = new int[n];
        int compId = 0;
        int maxReach = farthest[0];
        component[0] = 0;
        for (int i = 1; i < n; i++) {
            if (i > maxReach) compId++; // gap found, new component starts
            component[i] = compId;
            maxReach = Math.max(maxReach, farthest[i]);
        }

        // binary lifting table on farthest jumps, for O(log n) min-hop queries
        int LOG = 1;
        while ((1 << LOG) < n) LOG++;
        LOG++;

        int[][] up = new int[LOG][n];
        up[0] = farthest;
        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int q = queries.length;
        int[] answer = new int[q];

        for (int i = 0; i < q; i++) {
            int u = posInSorted[queries[i][0]];
            int v = posInSorted[queries[i][1]];

            if (u == v) {
                answer[i] = 0;
                continue;
            }

            if (u > v) { int tmp = u; u = v; v = tmp; } // always jump left to right

            if (component[u] != component[v]) {
                answer[i] = -1;
                continue;
            }

            // greedy binary lifting: take biggest jump that still undershoots v
            int cur = u;
            int steps = 0;
            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < v) {
                    cur = up[k][cur];
                    steps += (1 << k);
                }
            }
            steps++; // final hop lands on or past v

            answer[i] = steps;
        }

        return answer;
    }
}