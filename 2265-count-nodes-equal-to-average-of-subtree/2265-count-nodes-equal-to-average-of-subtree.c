/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     struct TreeNode *left;
 *     struct TreeNode *right;
 * };
 */

// Helper structure to mimic returning a 2-element array [sum, count]
struct SubtreeData {
    int sum;
    int count;
};

struct SubtreeData dfs(struct TreeNode* node, int* answer) {
    if (node == NULL) {
        struct SubtreeData base = {0, 0};
        return base;
    }

    struct SubtreeData left = dfs(node->left, answer);
    struct SubtreeData right = dfs(node->right, answer);

    struct SubtreeData current;
    current.sum = node->val + left.sum + right.sum;
    current.count = 1 + left.count + right.count;

    if (current.sum / current.count == node->val) {
        (*answer)++;
    }

    return current;
}

int averageOfSubtree(struct TreeNode* root) {
    int answer = 0;
    dfs(root, &answer);
    return answer;
}

