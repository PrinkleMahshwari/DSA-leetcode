/**
 * @param {number[]} nums
 * @param {number} k
 * @return {number}
 */
var maxSum = function(nums, k) {
    const bits = new Array(31).fill(0);
    const MOD = 1000000007n; // Using BigInt to prevent 64-bit integer overflow

    // Count how many numbers have each bit set
    for (const num of nums) {
        for (let b = 0; b < 31; b++) {
            if ((num & (1 << b)) !== 0) {
                bits[b]++;
            }
        }
    }

    let result = 0n;

    // Greedily build the largest k numbers
    for (let i = 0; i < k; i++) {
        let num = 0n;

        for (let b = 0; b < 31; b++) {
            if (bits[b] > 0) {
                num |= (1n << BigInt(b));
                bits[b]--;
            }
        }

        result = (result + num * num) % MOD;
    }

    return Number(result);
};
