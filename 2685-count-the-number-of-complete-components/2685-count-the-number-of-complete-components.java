class Solution {

    public int countCompleteComponents(int n, int[][] edges) {

        // n <= 50, so byte is enough for parent/size (max value 50),
        // short is enough for edgeCount (max ~1225), shaves a few bytes per array
        // vs using int[] everywhere
        byte[] parent = new byte[n];
        byte[] size = new byte[n];
        short[] edgeCount = new short[n];

        for (int i = 0; i < n; i++) {
            parent[i] = (byte) i;
            size[i] = 1;
        }

        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];

            // iterative find with path compression, no recursion frames on the call stack
            int rootA = find(parent, a);
            int rootB = find(parent, b);

            if (rootA == rootB) {
                edgeCount[rootA]++;
                continue;
            }

            if (size[rootA] < size[rootB]) {
                int tmp = rootA; rootA = rootB; rootB = tmp;
            }

            parent[rootB] = (byte) rootA;
            size[rootA] += size[rootB];
            edgeCount[rootA] += edgeCount[rootB] + 1;
        }

        int count = 0;

        for (int i = 0; i < n; i++) {
            if (find(parent, i) == i) {
                int m = size[i];
                int expectedEdges = m * (m - 1) / 2; // max ~1225, fits int easily
                if (edgeCount[i] == expectedEdges) {
                    count++;
                }
            }
        }

        return count;
    }

    // iterative path compression: first pass finds root, second pass flattens the chain
    private int find(byte[] parent, int x) {
        int root = x;
        while (parent[root] != root) {
            root = parent[root];
        }

        while (parent[x] != root) {
            int next = parent[x];
            parent[x] = (byte) root;
            x = next;
        }

        return root;
    }
}