class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        ArrayList<Integer>[] tree = new ArrayList[n];
        for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) tree[parent[i]].add (i);

        int[] newParent = parent.clone ();
        int[] last = new int[26];
        Arrays.fill (last, -1);

        int[] stack = new int[2 * n];
        int top = 0;
        stack[top++] = 0;

        int[] restore = new int[n];

        while (top > 0) {
            int state = stack[--top];
            if (state < 0) {
                int node = -state - 1;
                last[s.charAt(node) - 'a'] = restore[node];
                continue;
            }

            int node = state;
            int c = s.charAt (node) - 'a';
            restore[node] = last[c];
            if (node != 0 && last[c] != -1) newParent[node] = last[c];
            last[c] = node;
            stack[top++] = -node - 1;
            for (int child : tree[node]) stack[top++] = child;
        }

        // build modified tree
        ArrayList<Integer>[] modified = new ArrayList[n];
        for (int i = 0; i < n; i++) modified[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) modified[newParent[i]].add (i);

        // get traversal order of the modified tree
        int[] order = new int[n];
        top = 0;
        int size = 0;
        stack[top++] = 0;

        while (top > 0) {
            int node = stack[--top];
            order[size++] = node;
            for (int child : modified[node]) stack[top++] = child;
        }

        // calculate subtree sizes bottom-up
        int[] ans = new int[n];
        Arrays.fill (ans, 1);
        for (int i = n - 1; i >= 1; i--) {
            int node = order[i];
            ans[newParent[node]] += ans[node];
        }

        return ans;
    }
}