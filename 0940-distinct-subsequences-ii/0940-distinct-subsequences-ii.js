/**
 * @param {string} s
 * @return {number}
 */
var distinctSubseqII = function(s) {
    const MOD = 1000000007;

    let dp = 1;
    const last = new Array(26).fill(0);

    for (let i = 0; i < s.length; i++) {
        const index = s.charCodeAt(i) - 97; // 97 is the ASCII code for 'a'

        const previous = dp;
        // Adding MOD handles potential negative numbers safely before taking the remainder
        dp = (2 * dp - last[index] + MOD) % MOD;
        last[index] = previous;
    }

    return (dp - 1 + MOD) % MOD;
};
