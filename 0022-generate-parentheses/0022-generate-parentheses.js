/**
 * @param {number} n
 * @return {string[]}
 */
var generateParenthesis = function(n) {
    const result = [];
    const current = [];

    function backtrack(open, close) {
        // complete valid combination
        if (current.length === 2 * n) {
            result.push(current.join(''));
            return;
        }

        // add opening parenthesis
        if (open < n) {
            current.push('(');
            backtrack(open + 1, close);
            current.pop();
        }

        // add closing parenthesis only when valid
        if (close < open) {
            current.push(')');
            backtrack(open, close + 1);
            current.pop();
        }
    }

    backtrack(0, 0);
    return result;
};
