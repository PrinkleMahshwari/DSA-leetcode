import java.util.*;

class Solution {

    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        // Step 1: Sort nodes by their values.
        // The graph depends only on value differences, not on original indices.
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++)
            order[i] = i;

        Arrays.sort(order, (a, b) -> Integer.compare(nums[a], nums[b]));

        // sortedValue[i] = value at sorted position i
        // sortedPosition[originalIndex] = position after sorting
        int[] sortedValue = new int[n];
        int[] sortedPosition = new int[n];

        for (int i = 0; i < n; i++) {
            sortedValue[i] = nums[order[i]];
            sortedPosition[order[i]] = i;
        }

        // Step 2: Sliding Window
        // furthestReach[i] = furthest sorted position reachable in ONE graph edge.
        // Since sortedValue is non-decreasing, a single moving pointer gives O(n).
        int[] furthestReach = new int[n];
        int right = 0;

        for (int left = 0; left < n; left++) {

            if (right < left)
                right = left;

            while (right + 1 < n &&
                   sortedValue[right + 1] - sortedValue[left] <= maxDiff) {
                right++;
            }

            furthestReach[left] = right;
        }

        // Step 3: Connected Components
        // Every node represents an interval:
        //
        //      [ currentPosition ... furthestReach[currentPosition] ]
        //
        // If the next interval begins before the current merged interval ends,
        // both belong to the same connected component.
        // Otherwise, a brand-new component starts.
        int[] componentId = new int[n];

        int currentComponent = 0;
        int currentMaximumReach = furthestReach[0];

        componentId[0] = currentComponent;

        for (int i = 1; i < n; i++) {

            if (i > currentMaximumReach)
                currentComponent++;

            componentId[i] = currentComponent;

            currentMaximumReach =
                Math.max(currentMaximumReach, furthestReach[i]);
        }

        // Step 4: Binary Lifting
        //
        // jump[k][i] = furthest sorted position reachable after exactly 2^k edges
        // starting from sorted position i.
        int LOG = 32 - Integer.numberOfLeadingZeros(n);

        int[][] jump = new int[LOG][n];

        System.arraycopy(furthestReach, 0, jump[0], 0, n);

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                jump[k][i] = jump[k - 1][ jump[k - 1][i] ];
            }
        }

        int q = queries.length;
        int[] answer = new int[q];

        // Step 5: Answer each query independently.
        for (int i = 0; i < q; i++) {

            int left =
                sortedPosition[queries[i][0]];

            int rightPosition =
                sortedPosition[queries[i][1]];

            // same node
            if (left == rightPosition) {
                answer[i] = 0;
                continue;
            }

            // always jump from left -> right
            if (left > rightPosition) {
                int temp = left;
                left = rightPosition;
                rightPosition = temp;
            }

            // different connected components
            if (componentId[left] != componentId[rightPosition]) {
                answer[i] = -1;
                continue;
            }

            int current = left;
            int minimumEdges = 0;

            // Greedily take the largest jump that still stays before the target.
            for (int k = LOG - 1; k >= 0; k--) {

                if (jump[k][current] < rightPosition) {
                    current = jump[k][current];
                    minimumEdges += 1 << k;
                }
            }

            // Final edge reaches (or passes) the destination.
            answer[i] = minimumEdges + 1;
        }

        return answer;
    }
}