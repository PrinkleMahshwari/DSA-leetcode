#include <stdlib.h>
#include <string.h>

// Global-like structured function variables handled safely on memory frames
void buildSegmentTree(int node, int left, int right, char* s, int* length, char* leftChar, char* rightChar, int* prefix, int* suffix, int* max, int* leafNodeMap) {
    length[node] = right - left + 1;
    if (left == right) {
        leftChar[node] = s[left];
        rightChar[node] = s[left];
        prefix[node] = 1;
        suffix[node] = 1;
        max[node] = 1;
        leafNodeMap[left] = node;
        return;
    }

    int mid = left + (right - left) / 2;
    int leftChild = node << 1;
    int rightChild = leftChild | 1;

    buildSegmentTree(leftChild, left, mid, s, length, leftChar, rightChar, prefix, suffix, max, leafNodeMap);
    buildSegmentTree(rightChild, mid + 1, right, s, length, leftChar, rightChar, prefix, suffix, max, leafNodeMap);

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
        if (combo > maxVal) maxVal = combo;
    }
    max[node] = maxVal;
}

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* longestRepeating(char* s, char* queryCharacters, int* queryIndices, int queryIndicesSize, int* returnSize) {
    int n = strlen(s);
    int k = queryIndicesSize;
    int treeSize = 4 * n;

    char* leftChar = (char*)malloc(treeSize * sizeof(char));
    char* rightChar = (char*)malloc(treeSize * sizeof(char));
    int* prefix = (int*)malloc(treeSize * sizeof(int));
    int* suffix = (int*)malloc(treeSize * sizeof(int));
    int* max = (int*)malloc(treeSize * sizeof(int));
    int* length = (int*)malloc(treeSize * sizeof(int));
    int* leafNodeMap = (int*)malloc(n * sizeof(int));

    buildSegmentTree(1, 0, n - 1, s, length, leftChar, rightChar, prefix, suffix, max, leafNodeMap);

    int* answer = (int*)malloc(k * sizeof(int));

    for (int i = 0; i < k; i++) {
        int node = leafNodeMap[queryIndices[i]];
        char ch = queryCharacters[i];

        leftChar[node] = ch;
        rightChar[node] = ch;

        node >>= 1;
        while (node > 0) {
            int leftChild = node << 1;
            int rightChild = leftChild | 1;

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
                if (combo > maxVal) maxVal = combo;
            }
            max[node] = maxVal;

            node >>= 1;
        }
        answer[i] = max[1];
    }

    free(leftChar);
    free(rightChar);
    free(prefix);
    free(suffix);
    free(max);
    free(length);
    free(leafNodeMap);

    *returnSize = k;
    return answer;
}
