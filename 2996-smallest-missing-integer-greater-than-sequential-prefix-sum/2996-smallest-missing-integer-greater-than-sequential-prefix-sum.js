/**
 * @param {number[]} nums
 * @return {number}
 */
var missingInteger = function(nums) {
    let prefixSum = nums[0];
    
    // Step 1: Calculate the sum of the longest sequential prefix
    for (let i = 1; i < nums.length; i++) {
        if (nums[i] === nums[i - 1] + 1) {
            prefixSum += nums[i];
        } else {
            break; // Stop when the sequence breaks
        }
    }
    
    // Step 2: Add all elements to a Set for efficient lookups
    const numSet = new Set(nums);
    
    // Step 3: Find the smallest missing integer >= prefixSum
    while (numSet.has(prefixSum)) {
        prefixSum++;
    }
    
    return prefixSum;
};
