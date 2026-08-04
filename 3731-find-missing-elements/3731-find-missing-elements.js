/**
 * @param {number[]} nums
 * @return {number[]}
 */
var findMissingElements = function(nums) {
    const n = nums.length;
    if (n === 0) return [];

    // Find min and max in one pass
    let min = nums[0];
    let max = nums[0];
    for (let i = 1; i < n; i++) {
        if (nums[i] < min) min = nums[i];
        if (nums[i] > max) max = nums[i];
    }

    // Using a Uint8Array or standard array as a boolean buffer map
    const range = max - min + 1;
    const present = new Uint8Array(range);
    for (let i = 0; i < n; i++) {
        present[nums[i] - min] = 1;
    }

    // Collecting missing interior elements in sorted order
    const result = [];
    for (let i = 1; i < range - 1; i++) {
        if (present[i] === 0) {
            result.push(i + min); // Converting back to actual value
        }
    }

    return result;
};
