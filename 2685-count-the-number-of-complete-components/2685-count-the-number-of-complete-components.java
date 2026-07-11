class Solution {

    private int[] parent;
    private int[] size;
    private int[] edgeCount;

    public int countCompleteComponents(int n, int[][] edges) {
        
        // union-find, smll n <= 50 so no rank/path compression needed for perf,
        // but path compression added anyway since it's basically free
        parent = new int[n];
        size = new int[n];
        edgeCount = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        for (int[] edge : edges)
            union(edge[0], edge[1]);
        
        int count = 0;

        // for each root, component is complete iff edges == m*(m-1)/2
        for (int i = 0; i < n; i++) {
            if (find(i) == i) {
                int m = size[i];
                long expectedEdges = (long) m * (m - 1) / 2;
                if (edgeCount[i] == expectedEdges)
                    count++;
            }
        }

        return count;        
    }

    private int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        
        return parent[x];
    }

    private void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            // both endpoints already in same component, just count the edge there
            edgeCount[rootA]++;
            return;
        }

        // merge smaller into larger, caryy over size and ege count
        if (size[rootA] < size[rootB]) {
            int tmp = rootA; rootA = rootB; rootB = tmp;
        }

        parent[rootB] = rootA;
        size[rootA] += size[rootB];
        edgeCount[rootA] += edgeCount[rootB] + 1; // +1 for the connecting edge itself
    }
}