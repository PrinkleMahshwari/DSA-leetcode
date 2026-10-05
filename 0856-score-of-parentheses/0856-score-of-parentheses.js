/**
 * @param {string} s
 * @return {number}
 */
var scoreOfParentheses = function(s) {
    let score = 0;
    let depth = 0;

    for (let i = 0; i < s.length; i++) {
        if (s[i] === '(') {
            depth++;
        } else {
            depth--;

            // If it forms the core string "()", add its value based on depth
            if (s[i - 1] === '(') {
                score += 1 << depth;
            }
        }
    }

    return score;
};
