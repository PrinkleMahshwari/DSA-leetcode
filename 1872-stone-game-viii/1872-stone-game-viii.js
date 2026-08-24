/**
 * @param {number[]} stones
 * @return {number}
 */
var stoneGameVIII = function(stones) {
    const n = stones.length;

    // Use BigInt or flat structures to prevent number truncation on long ranges
    const prefix = new Float64Array(n);
    prefix[0] = stones[0];

    for (let i = 1; i < n; i++) {
        prefix[i] = prefix[i - 1] + stones[i];
    }

    let ans = prefix[n - 1];

    for (let i = n - 2; i >= 1; i--) {
        ans = Math.max(ans, prefix[i] - ans);
    }

    return ans;
};
