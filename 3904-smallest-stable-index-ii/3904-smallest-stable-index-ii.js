/**
 * @param {number[]} nums
 * @param {number} k
 * @return {number}
 */
var firstStableIndex = function(nums, k) {
    const n = nums.length;
    if (n === 0) return -1;

    const prefixMax = new Array(n);
    const suffMin = new Array(n);

    // Compute prefix maximums
    prefixMax[0] = nums[0];
    for (let i = 1; i < n; i++) {
        prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
    }

    // Compute suffix minimums
    suffMin[n - 1] = nums[n - 1];
    for (let i = n - 2; i >= 0; i--) {
        suffMin[i] = Math.min(suffMin[i + 1], nums[i]);
    }

    // Find smallest stable index
    for (let i = 0; i < n; i++) {
        if (prefixMax[i] - suffMin[i] <= k) {
            return i;
        }
    }

    return -1;
};