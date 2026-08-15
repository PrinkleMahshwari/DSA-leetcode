/**
 * @param {number[]} nums
 * @return {number}
 */
var longestSubsequence = function(nums) {
    let xor = 0;
    let hasNonZero = false;

    for (let i = 0; i < nums.length; i++) {
        const num = nums[i];
        xor ^= num;

        if (num !== 0) {
            hasNonZero = true;
        }
    }

    if (xor !== 0) {
        return nums.length;
    }

    return hasNonZero ? nums.length - 1 : 0;
};
