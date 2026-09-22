class Solution {

    static class Node {
        int product;
        int[] count;

        Node(int k) {
            count = new int[k];
        }
    }

    private int k;
    private Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;

        int n = nums.length;
        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] answer = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Persistent point update
            update(1, 0, n - 1, index, value);

            // Get information for nums[start ... n-1]
            Node result = query(1, 0, n - 1, start, n - 1);

            answer[q] = result.count[x];
        }

        return answer;
    }

    private void build(int node, int left, int right, int[] nums) {
        if (left == right) {
            tree[node] = new Node(k);

            int remainder = nums[left] % k;

            tree[node].product = remainder;
            tree[node].count[remainder] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid, nums);
        build(node * 2 + 1, mid + 1, right, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(int node, int left, int right, int index, int value) {
        if (left == right) {
            tree[node] = new Node(k);

            int remainder = value % k;

            tree[node].product = remainder;
            tree[node].count[remainder] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node query(int node, int left, int right, int queryLeft, int queryRight) {

        if (queryLeft <= left && right <= queryRight) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        if (queryRight <= mid) {
            return query(node * 2, left, mid, queryLeft, queryRight);
        }

        if (queryLeft > mid) {
            return query(node * 2 + 1, mid + 1, right,
                         queryLeft, queryRight);
        }

        Node leftResult =
            query(node * 2, left, mid, queryLeft, queryRight);

        Node rightResult =
            query(node * 2 + 1, mid + 1, right,
                  queryLeft, queryRight);

        return merge(leftResult, rightResult);
    }

    private Node merge(Node left, Node right) {
        Node result = new Node(k);

        // Product of the complete merged segment
        result.product = (int) ((long) left.product * right.product % k);

        // Prefixes entirely inside the left segment
        for (int r = 0; r < k; r++) {
            result.count[r] = left.count[r];
        }

        // Prefixes that continue from left into right
        for (int r = 0; r < k; r++) {
            int newRemainder =
                (int) ((long) left.product * r % k);

            result.count[newRemainder] += right.count[r];
        }

        return result;
    }
}