/**
 * @param {number[]} nums
 * @return {number[]}
 */
var resultArray = function(nums) {
    const arr1 = [];
    const arr2 = [];

    // first two operations are fixed
    arr1.push(nums[0]);
    arr2.push(nums[1]);

    let last1 = nums[0];
    let last2 = nums[1];

    // process remaining elements
    for (let i = 2; i < nums.length; i++) {
        if (last1 > last2) {
            arr1.push(nums[i]);
            last1 = nums[i];
        } else {
            arr2.push(nums[i]);
            last2 = nums[i];
        }
    }

    // concatenate arr1 and arr2
    return arr1.concat(arr2);
};
