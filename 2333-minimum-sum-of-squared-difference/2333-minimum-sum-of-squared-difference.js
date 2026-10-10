/**
 * @param {number[]} nums1
 * @param {number[]} nums2
 * @param {number} k1
 * @param {number} k2
 * @return {number}
 */
var minSumSquareDiff = function(nums1, nums2, k1, k2) {
    const n = nums1.length;
    const diff = new Int32Array(n);

    let maxDiff = 0;
    let totalDiff = 0;

    for (let i = 0; i < n; i++) {
        diff[i] = Math.abs(nums1[i] - nums2[i]);
        if (diff[i] > maxDiff) maxDiff = diff[i];
        totalDiff += diff[i];
    }

    const k = k1 + k2;

    // If all differences can become zero
    if (k >= totalDiff) return 0;

    // Binary search for the smallest possible max difference
    let low = 0;
    let high = maxDiff;

    while (low < high) {
        const mid = low + Math.floor((high - low) / 2);
        let needed = 0;

        for (let i = 0; i < n; i++) {
            if (diff[i] > mid) needed += diff[i] - mid;
        }

        if (needed <= k) {
            high = mid;
        } else {
            low = mid + 1;
        }
    }

    const limit = low;
    let needed = 0;
    let result = 0;

    for (let i = 0; i < n; i++) {
        const reduced = Math.min(diff[i], limit);
        needed += diff[i] - reduced;
        result += reduced * reduced;
    }

    // Use remaining operations to reduce some differences by one more
    const remaining = k - needed;
    result -= remaining * (2 * limit - 1);

    return result;
};
