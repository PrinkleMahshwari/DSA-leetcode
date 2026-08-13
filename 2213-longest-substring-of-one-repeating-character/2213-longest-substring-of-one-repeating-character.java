class Solution {
    // Parallel arrays to completely eliminate Node objects and flatten the memory footprint
    private char[] leftChar;
    private char[] rightChar;
    private int[] prefix;
    private int[] suffix;
    private int[] max;
    private int[] length;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int k = queryIndices.length;

        // Using exactly 4 * n elements for flat, cache-friendly primitive segment tree representation
        int treeSize = 4 * n;
        leftChar = new char[treeSize];
        rightChar = new char[treeSize];
        prefix = new int[treeSize];
        suffix = new int[treeSize];
        max = new int[treeSize];
        length = new int[treeSize];

        // Avoid repeated string extraction wrappers in inner hot loops
        char[] sArr = s.toCharArray();
        char[] qChars = queryCharacters.toCharArray();

        build(1, 0, n - 1, sArr);

        int[] answer = new int[k];
        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], qChars[i]);
            answer[i] = max[1]; // The global maximum answer is always stored at root node index 1
        }

        return answer;
    }

    private void build(int node, int left, int right, char[] s) {
        if (left == right) {
            initLeaf(node, s[left]);
            return;
        }

        int mid = left + (right - left) / 2;
        int leftChild = node << 1;       // Optimized bitwise left-shift (node * 2)
        int rightChild = leftChild | 1;   // Optimized bitwise bit-or (node * 2 + 1)

        build(leftChild, left, mid, s);
        build(rightChild, mid + 1, right, s);

        merge(node, leftChild, rightChild);
    }

    private void update(int node, int left, int right, int index, char ch) {
        if (left == right) {
            initLeaf(node, ch);
            return;
        }

        int mid = left + (right - left) / 2;
        int leftChild = node << 1;
        int rightChild = leftChild | 1;

        if (index <= mid) {
            update(leftChild, left, mid, index, ch);
        } else {
            update(rightChild, mid + 1, right, index, ch);
        }

        merge(node, leftChild, rightChild);
    }

    // Inline leaf node initialization to prevent any object/variable allocation footprint
    private void initLeaf(int node, char c) {
        leftChar[node] = c;
        rightChar[node] = c;
        prefix[node] = 1;
        suffix[node] = 1;
        max[node] = 1;
        length[node] = 1;
    }

    // In-place primitive merging to avoid generating temporary object churn
    private void merge(int parent, int a, int b) {
        leftChar[parent] = leftChar[a];
        rightChar[parent] = rightChar[b];
        length[parent] = length[a] + length[b];

        // Merge Prefix
        int aLen = length[a];
        int aPrefix = prefix[a];
        if (aPrefix == aLen && rightChar[a] == leftChar[b]) {
            prefix[parent] = aLen + prefix[b];
        } else {
            prefix[parent] = aPrefix;
        }

        // Merge Suffix
        int bLen = length[b];
        int bSuffix = suffix[b];
        if (bSuffix == bLen && rightChar[a] == leftChar[b]) {
            suffix[parent] = bLen + suffix[a];
        } else {
            suffix[parent] = bSuffix;
        }

        // Merge Maximum
        int maxVal = max[a] > max[b] ? max[a] : max[b];
        if (rightChar[a] == leftChar[b]) {
            int combo = suffix[a] + prefix[b];
            if (combo > maxVal) {
                maxVal = combo;
            }
        }
        max[parent] = maxVal;
    }
}
