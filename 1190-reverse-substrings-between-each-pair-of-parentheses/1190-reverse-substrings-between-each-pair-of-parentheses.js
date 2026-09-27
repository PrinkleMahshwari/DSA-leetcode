/**
 * @param {string} s
 * @return {string}
 */
var reverseParentheses = function(s) {
    const n = s.length;
    const pair = new Int32Array(n);
    const stack = [];

    // Find matching parentheses
    for (let i = 0; i < n; i++) {
        if (s[i] === '(') {
            stack.push(i);
        } else if (s[i] === ')') {
            const open = stack.pop();
            pair[open] = i;
            pair[i] = open;
        }
    }

    const result = [];
    let i = 0;
    let direction = 1;

    while (i >= 0 && i < n) {
        const current = s[i];

        if (current === '(' || current === ')') {
            // Jump to the matching parenthesis
            i = pair[i];
            // Reverse traversal direction
            direction = -direction;
        } else {
            result.push(current);
        }

        i += direction;
    }

    return result.join('');
};
