class Solution {
    // Highly efficient primitive parallel arrays to eliminate node object overhead entirely
    private char[] leftChar;
    private char[] rightChar;
    private int[] prefix;
    private int[] suffix;
    private int[] max;
    private int[] length;

    // Cache the structural tree index maps for direct bottom-up updates
    private int[] leafNodeMap;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int k = queryIndices.length;

        // Perfectly balanced 4 * n flattened representation
        int treeSize = 4 * n;
        leftChar = new char[treeSize];
        rightChar = new char[treeSize];
        prefix = new int[treeSize];
        suffix = new int[treeSize];
        max = new int[treeSize];
        length = new int[treeSize];
        
        // Map string positions to their exact segment tree leaf node indices
        leafNodeMap = new int[n];

        char[] sArr = s.toCharArray();
        char[] qChars = queryCharacters.toCharArray();

        build(1, 0, n - 1, sArr);

        int[] answer = new int[k];
        for (int i = 0; i < k; i++) {
            // Retrieve leaf index in O(1) time and look upward immediately
            int node = leafNodeMap[queryIndices[i]];
            char ch = qChars[i];

            leftChar[node] = ch;
            rightChar[node] = ch;

            // Shift straight up to update parent associations
            node >>= 1;
            while (node > 0) {
                int leftChild = node << 1;
                int rightChild = leftChild | 1;

                leftChar[node] = leftChar[leftChild];
                rightChar[node] = rightChar[rightChild];

                // Merge Prefix state properties
                int aLen = length[leftChild];
                if (prefix[leftChild] == aLen && rightChar[leftChild] == leftChar[rightChild]) {
                    prefix[node] = aLen + prefix[rightChild];
                } else {
                    prefix[node] = prefix[leftChild];
                }

                // Merge Suffix state properties
                int bLen = length[rightChild];
                if (suffix[rightChild] == bLen && rightChar[leftChild] == leftChar[rightChild]) {
                    suffix[node] = bLen + suffix[leftChild];
                } else {
                    suffix[node] = suffix[rightChild];
                }

                // Calculate current branch maximum
                int maxVal = max[leftChild] > max[rightChild] ? max[leftChild] : max[rightChild];
                if (rightChar[leftChild] == leftChar[rightChild]) {
                    int combo = suffix[leftChild] + prefix[rightChild];
                    if (combo > maxVal) {
                        maxVal = combo;
                    }
                }
                max[node] = maxVal;

                node >>= 1; // Bitwise shift to ascend to the next parent layer
            }

            answer[i] = max[1]; // Segment tree root node 1 always contains the correct absolute answer
        }

        return answer;
    }

    private void build(int node, int left, int right, char[] s) {
        length[node] = right - left + 1;
        
        if (left == right) {
            leftChar[node] = s[left];
            rightChar[node] = s[left];
            prefix[node] = 1;
            suffix[node] = 1;
            max[node] = 1;
            leafNodeMap[left] = node; // Save the exact mapped index point
            return;
        }

        int mid = left + (right - left) / 2;
        int leftChild = node << 1;
        int rightChild = leftChild | 1;

        build(leftChild, left, mid, s);
        build(rightChild, mid + 1, right, s);

        // Standard merge process for tree initialization
        leftChar[node] = leftChar[leftChild];
        rightChar[node] = rightChar[rightChild];

        int aLen = length[leftChild];
        if (prefix[leftChild] == aLen && rightChar[leftChild] == leftChar[rightChild]) {
            prefix[node] = aLen + prefix[rightChild];
        } else {
            prefix[node] = prefix[leftChild];
        }

        int bLen = length[rightChild];
        if (suffix[rightChild] == bLen && rightChar[leftChild] == leftChar[rightChild]) {
            suffix[node] = bLen + suffix[leftChild];
        } else {
            suffix[node] = suffix[rightChild];
        }

        int maxVal = max[leftChild] > max[rightChild] ? max[leftChild] : max[rightChild];
        if (rightChar[leftChild] == leftChar[rightChild]) {
            int combo = suffix[leftChild] + prefix[rightChild];
            if (combo > maxVal) {
                maxVal = combo;
            }
        }
        max[node] = maxVal;
    }
}
