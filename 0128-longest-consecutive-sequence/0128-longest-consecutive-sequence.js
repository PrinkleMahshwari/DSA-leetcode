/**
 * @param {number[]} nums
 * @return {number}
 */
var longestConsecutive = function(nums) {
    if (nums.length === 0) return 0;

    const set = new Set();
    
    // Store all numbers
    for (let i = 0; i < nums.length; i++) {
        set.add(nums[i]);
    }

    let longest = 0;

    for (const num of set) {
        // Check if this number is the start of a sequence
        if (!set.has(num - 1)) {
            let current = num;
            let length = 1;

            // Count consecutive numbers
            while (set.has(current + 1)) {
                current++;
                length++;
            }

            longest = Math.max(longest, length);
        }
    }

    return longest;
};
