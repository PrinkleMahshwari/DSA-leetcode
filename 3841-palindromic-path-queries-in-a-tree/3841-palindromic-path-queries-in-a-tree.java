import java.util.*;

class Solution {
    private int n, log;
    private List<Integer>[] adj;
    private int[] tin, tout, depth, parent, prefix;
    private int[][] up;
    private int[] bit;
    private Fenwick fenwick;
    private char[] chars;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        this.n = n;
        chars = s.toCharArray();

        bit = new int[26];
        for (int i = 0; i < 26; i++) {
            bit[i] = 1 << i;
        }

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        log = 1;
        while ((1 << log) <= n) {
            log++;
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        parent = new int[n];
        prefix = new int[n];
        up = new int[log][n];

        buildTree();

        fenwick = new Fenwick(n);
        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int delta = bit[chars[u] - 'a'] ^ bit[c - 'a'];

                fenwick.rangeXor(tin[u], tout[u], delta);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int maskU = getPrefix(u);
                int maskV = getPrefix(v);
                int ancestor = lca(u, v);

                int pathMask = maskU ^ maskV
                             ^ bit[chars[ancestor] - 'a'];

                answer.add(Integer.bitCount(pathMask) <= 1);
            }
        }

        return answer;
    }

    private void buildTree() {
        int[] order = new int[n];
        int[] stack = new int[n];
        int top = 0;
        int size = 0;

        parent[0] = 0;
        prefix[0] = bit[chars[0] - 'a'];
        stack[top++] = 0;

        while (top > 0) {
            int u = stack[--top];

            tin[u] = size;
            order[size++] = u;

            for (int v : adj[u]) {
                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                prefix[v] = prefix[u] ^ bit[chars[v] - 'a'];

                stack[top++] = v;
            }
        }

        for (int i = 0; i < n; i++) {
            tout[i] = tin[i];
        }

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            int p = parent[u];
            tout[p] = Math.max(tout[p], tout[u]);
        }

        for (int u = 0; u < n; u++) {
            up[0][u] = parent[u];
        }

        for (int j = 1; j < log; j++) {
            for (int u = 0; u < n; u++) {
                up[j][u] = up[j - 1][up[j - 1][u]];
            }
        }
    }

    private int getPrefix(int u) {
        return prefix[u] ^ fenwick.pointQuery(tin[u]);
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < log; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = log - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }

    private static class Fenwick {
        private final int[] tree;
        private final int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        private void update(int i, int value) {
            for (i++; i <= n; i += i & -i) {
                tree[i] ^= value;
            }
        }

        void rangeXor(int left, int right, int value) {
            update(left, value);

            if (right + 1 < n) {
                update(right + 1, value);
            }
        }

        int pointQuery(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}