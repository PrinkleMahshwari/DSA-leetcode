/**
 * @param {string} s
 * @return {number}
 */
var reverseDegree = function(s) {
    let result = 0;

    for (let i = 0; i < s.length; i++) {
        const reverseValue = 26 - (s.charCodeAt(i) - 97); // 97 is the ASCII code for 'a'
        result += reverseValue * (i + 1);
    }

    return result;
};
