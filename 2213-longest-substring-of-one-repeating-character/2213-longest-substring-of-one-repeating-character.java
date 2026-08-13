class Solution {

    static class Node {
        char leftChar;
        char rightChar;

        int prefix;
        int suffix;
        int max;
        int length;

        Node(char c) {
            leftChar = c;
            rightChar = c;
            prefix = 1;
            suffix = 1;
            max = 1;
            length = 1;
        }
    }

    Node[] tree;

    public int[] longestRepeating(
            String s,
            String queryCharacters,
            int[] queryIndices) {

        int n = s.length();
        int k = queryIndices.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1, s);

        int[] answer = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            update(1, 0, n - 1, index, ch);

            answer[i] = tree[1].max;
        }

        return answer;
    }

    private void build(int node, int left, int right, String s) {

        if (left == right) {
            tree[node] = new Node(s.charAt(left));
            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid, s);
        build(node * 2 + 1, mid + 1, right, s);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(
            int node,
            int left,
            int right,
            int index,
            char ch) {

        if (left == right) {
            tree[node] = new Node(ch);
            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, ch);
        } else {
            update(node * 2 + 1, mid + 1, right, index, ch);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node merge(Node a, Node b) {

        Node res = new Node(a.leftChar);

        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        res.length = a.length + b.length;

        // Prefix
        res.prefix = a.prefix;

        if (a.prefix == a.length && a.rightChar == b.leftChar) {
            res.prefix = a.length + b.prefix;
        }

        // Suffix
        res.suffix = b.suffix;

        if (b.suffix == b.length && a.rightChar == b.leftChar) {
            res.suffix = b.length + a.suffix;
        }

        // Maximum
        res.max = Math.max(a.max, b.max);

        if (a.rightChar == b.leftChar) {
            res.max = Math.max(
                res.max,
                a.suffix + b.prefix
            );
        }

        return res;
    }
}