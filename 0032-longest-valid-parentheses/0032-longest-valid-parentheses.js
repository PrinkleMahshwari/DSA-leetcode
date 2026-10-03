/**
 * @param {string} s
 * @return {number}
 */
var longestValidParentheses = function(s) {
    let maxLength = 0;
    const stack = new Int32Array(s.length + 1);
    let top = 0;

    // boundary before the string starts
    stack[top] = -1;

    for (let i = 0; i < s.length; i++) {
        if (s[i] === '(') {
            stack[++top] = i;
        } else {
            top--;

            // unmatched ')'
            if (top < 0) {
                top = 0;
                stack[top] = i;
            } else {
                maxLength = Math.max(maxLength, i - stack[top]);
            }
        }
    }

    return maxLength;
};
