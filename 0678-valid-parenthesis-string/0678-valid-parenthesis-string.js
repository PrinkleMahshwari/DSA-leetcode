/**
 * @param {string} s
 * @return {boolean}
 */
var checkValidString = function(s) {
    let low = 0;
    let high = 0;

    for (let i = 0; i < s.length; i++) {
        const ch = s[i];

        if (ch === '(') {
            low++;
            high++;
        } else if (ch === ')') {
            low--;
            high--;
        } else {
            // '*'
            low--;
            high++;
        }

        // minimum balance cannot be negative
        low = Math.max(0, low);

        // even the maximum balance is negative
        if (high < 0) return false;
    }

    return low === 0;
};
