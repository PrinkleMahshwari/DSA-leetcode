/**
 * @param {number[]} nums
 * @param {number} k
 * @return {number}
 */
var largestInteger = function(nums, k) {
    const n = nums.length;

    // case 1: only one subarray exists (k == n)
    if (k === n) {
        let max = -1;
        for (let i = 0; i < n; i++) {
            max = Math.max(max, nums[i]);
        }
        return max;
    }

    const freq = new Int32Array(51);
    for (let i = 0; i < n; i++) {
        freq[nums[i]]++;
    }

    // case 2: every subarray has size 1
    if (k === 1) {
        for (let x = 50; x >= 0; x--) {
            if (freq[x] === 1) {
                return x;
            }
        }
        return -1;
    }

    // case 3: 1 < k < n
    let ans = -1;
    if (freq[nums[0]] === 1) {
        ans = Math.max(ans, nums[0]);
    }
    if (freq[nums[n - 1]] === 1) {
        ans = Math.max(ans, nums[n - 1]);
    }

    return ans;
};
