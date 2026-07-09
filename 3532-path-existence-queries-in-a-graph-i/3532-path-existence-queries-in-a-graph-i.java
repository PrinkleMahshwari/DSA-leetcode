class Solution {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        
        // nums is already sorted.
        // If every adjacent gap between two indices is <= maxDiff,
        // then those nodes are connected through intermediate nodes.
        // Therefore, each connected component forms one continuous segment.
        // Whenever an adjacent gap exceeds maxDiff, a brand-new component begins.
        
        int[] componentId = new int[n];
        componentId[0] = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] - nums[i - 1] <= maxDiff) {
                // still connected to the same running component
                componentId[i] = componentId[i - 1];
            } else {
                // gap too large, this starts a brand new component
                componentId[i] = componentId[i - 1] + 1;
            }
        }

        int q = queries.length;
        boolean[] answer = new boolean[q];

        // each query now resolves in true O(1): just compare component ids
        for (int i = 0; i < q; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            answer[i] = componentId[u] == componentId[v];
        }

        return answer;
    }
}