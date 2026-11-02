/**
 * @param {number[]} nums
 * @return {number}
 */
var minMoves2 = function(nums) {
    // In JavaScript, sort() must take a comparator function to sort numbers properly
    nums.sort((a, b) => a - b);

    const median = nums[Math.floor(nums.length / 2)];
    let moves = 0;

    for (const num of nums) {
        moves += Math.abs(num - median);
    }

    return moves;
};
