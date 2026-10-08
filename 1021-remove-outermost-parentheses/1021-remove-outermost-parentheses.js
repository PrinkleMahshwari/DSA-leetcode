/**
 * @param {string} s
 * @return {string}
 */
var removeOuterParentheses = function(s) {
    let result = "";
    let depth = 0;

    for (let i = 0; i < s.length; i++) {
        let ch = s[i];
        if (ch === '(') {
            if (depth > 0) result += ch;
            depth++;
        } else {
            depth--;
            if (depth > 0) result += ch;
        }
    }

    return result;
};
