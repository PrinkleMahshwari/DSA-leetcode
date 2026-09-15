/**
 * @param {string} s
 * @param {number} k
 * @return {number}
 */
var maxPalindromes = function(s, k) {
    const n = s.length;

    // build palindrome table
    const palindrome = Array.from({ length: n }, () => new Array(n).fill(false));

    for (let i = n - 1; i >= 0; i--) {
        for (let j = i; j < n; j++) {
            if (s.charAt(i) === s.charAt(j) && (j - i <= 2 || palindrome[i + 1][j - 1])) {
                palindrome[i][j] = true;
            }
        }
    }

    // dp[i] = max number of palindromes in s[0 ... i - 1]
    const dp = new Array(n + 1).fill(0);

    for (let i = 1; i <= n; i++) {
        // don't use a palindrome ending at i - 1
        dp[i] = dp[i - 1];

        // try every palindrome ending at i - 1
        for (let j = 0; j < i; j++) {
            if (i - j >= k && palindrome[j][i - 1]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
    }

    return dp[n];
};
