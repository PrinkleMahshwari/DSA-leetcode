class Solution {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        
        // nums is sorted, so for any i < j,
        // nums[j] - nums[i] >= nums[i+1] - nums[i] (since nums is non-decreasing,
        // every intermediate step's gap is <= total gap). 
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

        // each query now resolves in true O(1): just compate component ids
        for (int i = 0; i < q; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            answer[i] = componentId[u] == componentId[v];
        }

        return answer;
    }
}