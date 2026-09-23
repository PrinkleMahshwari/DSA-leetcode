/**
 * @param {number[]} nums
 * @param {number} x
 * @return {number}
 */
var minOperations = function(nums, x) {
    const n = nums.length;

    let total = 0;
    for (let i = 0; i < n; i++) total += nums[i];

    const target = total - x;

    if (target < 0) return -1;
    if (target === 0) return n;

    let left = 0;
    let sum = 0;
    let maxLength = -1;

    for (let right = 0; right < n; right++) {
        sum += nums[right];

        while (left <= right && sum > target) {
            sum -= nums[left++];
        }

        if (sum === target) {
            maxLength = Math.max(maxLength, right - left + 1);
        }
    }

    return maxLength === -1 ? -1 : n - maxLength;
};
