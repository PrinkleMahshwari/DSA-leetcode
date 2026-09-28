/**
 * @param {string} s
 * @return {number}
 */
var maxDepth = function(s) {
    let depth = 0;
    let maxDepth = 0;

    for (let i = 0; i < s.length; i++) {
        const ch = s[i];
        if (ch === '(') {
            depth++;
            maxDepth = Math.max(maxDepth, depth);
        } else if (ch === ')') {
            depth--;
        }
    }

    return maxDepth;
};
