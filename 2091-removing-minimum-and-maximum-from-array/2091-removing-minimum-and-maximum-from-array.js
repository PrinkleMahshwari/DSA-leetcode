/**
 * @param {number[]} nums
 * @return {number}
 */
var minimumDeletions = function(nums) {
    const n = nums.length;

    let minIndex = 0;
    let maxIndex = 0;

    // find min and max indices 
    for (let i = 0; i < n; i++) {
        if (nums[i] < nums[minIndex]) {
            minIndex = i;
        }
        
        if (nums[i] > nums[maxIndex]) {
            maxIndex = i;
        }
    }

    const left = Math.min(minIndex, maxIndex);
    const right = Math.max(minIndex, maxIndex);

    // remove both from the front
    const fromFront = right + 1;

    // remove both from the back
    const fromBack = n - left;

    // remove one from front, and one from back
    const fromBoth = (left + 1) + (n - right);

    return Math.min(fromFront, Math.min(fromBack, fromBoth));
};
