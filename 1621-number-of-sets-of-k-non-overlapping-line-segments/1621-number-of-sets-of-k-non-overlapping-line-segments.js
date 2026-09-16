/**
 * @param {number} n
 * @param {number} k
 * @return {number}
 */
var numberOfSets = function(n, k) {
    const MOD = 1000000007;

    let dp = new Array(n).fill(1);

    for (let j = 1; j <= k; j++) {
        const next = new Array(n).fill(0);
        let sum = 0;

        for (let i = 1; i < n; i++) {
            sum = (sum + dp[i - 1]) % MOD;
            next[i] = (next[i - 1] + sum) % MOD;
        }
        dp = next;
    }
    
    return dp[n - 1];
};
