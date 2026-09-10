/**
 * Definition for a binary tree node.
 * function TreeNode(val, left, right) {
 *     this.val = (val===undefined ? 0 : val)
 *     this.left = (left===undefined ? null : left)
 *     this.right = (right===undefined ? null : right)
 * }
 */
/**
 * @param {TreeNode} root
 * @return {number}
 */
var averageOfSubtree = function(root) {
    let answer = 0;

    function dfs(node) {
        // FIXED: Now correctly returns the base array structure
        if (node === null) {
            return [0, 0]; 
        }

        const left = dfs(node.left);
        const right = dfs(node.right);

        const sum = node.val + left[0] + right[0];
        const count = 1 + left[1] + right[1];

        if (Math.floor(sum / count) === node.val) {
            answer++;
        }

        return [sum, count];
    }

    dfs(root);
    return answer;
};

