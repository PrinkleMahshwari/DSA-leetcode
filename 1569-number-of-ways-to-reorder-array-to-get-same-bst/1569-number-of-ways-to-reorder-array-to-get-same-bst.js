/**
 * @param {number[]} nums
 * @return {number}
 */
var numOfWays = function(nums) {
    const MOD = 1000000007n;
    const n = nums.length;

    // Precompute combinations using Pascal's triangle
    const comb = Array.from({ length: n + 1 }, () => new Array(n + 1).fill(0n));

    for (let i = 0; i <= n; i++) {
        comb[i][0] = 1n;
        comb[i][i] = 1n;
        for (let j = 1; j < i; j++) {
            comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
        }
    }

    const solve = (list) => {
        const len = list.length;
        if (len <= 2) return 1n;

        const root = list[0];
        const left = [];
        const right = [];

        for (let i = 1; i < len; i++) {
            if (list[i] < root) {
                left.push(list[i]);
            } else {
                right.push(list[i]);
            }
        }

        // ways = (comb[n - 1][left.length] * solve(left) % MOD) * solve(right) % MOD
        let ways = comb[len - 1][left.length];
        ways = (ways * solve(left)) % MOD;
        ways = (ways * solve(right)) % MOD;

        return ways;
    };

    // Subtract the original ordering
    const totalWays = solve(nums);
    return Number((totalWays - 1n + MOD) % MOD);
};
