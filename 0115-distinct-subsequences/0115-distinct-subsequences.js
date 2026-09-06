/**
 * @param {string} s
 * @param {string} t
 * @return {number}
 */
var numDistinct = function(s, t) {
    const n = s.length;
    const m = t.length;

    // Initialize DP array with zeros, dp[0] = 1
    const dp = new Array(m + 1).fill(0);
    dp[0] = 1;

    for (let i = 0; i < n; i++) {
        for (let j = m; j >= 1; j--) {
            if (s.charAt(i) === t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
            }
        }
    }

    return dp[m];
};