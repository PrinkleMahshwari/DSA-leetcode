/**
 * @param {number[]} nums
 * @return {string[]}
 */
var summaryRanges = function(nums) {
    const result = [];
    let i = 0;

    while (i < nums.length) {
        const start = nums[i];

        // Track continuous consecutive integer jumps
        while (i + 1 < nums.length && nums[i + 1] === nums[i] + 1) {
            i++;
        }

        // Format single elements or interval blocks cleanly
        if (start === nums[i]) {
            result.push(String(start));
        } else {
            result.push(start + "->" + nums[i]);
        }

        i++;
    }

    return result;
};
