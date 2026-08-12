/**
 * @param {number[]} nums
 * @param {number} k
 * @return {number}
 */
var maxSubarrayLength = function(nums, k) {
    // Map to store the frequencies of elements
    const freq = new Map();

    let left = 0;
    let ans = 0;

    for (let right = 0; right < nums.length; right++) {
        const rNum = nums[right];
        freq.set(rNum, (freq.get(rNum) || 0) + 1);

        // Shrink the window from the left if the current element's frequency exceeds k
        while (freq.get(rNum) > k) {
            const lNum = nums[left];
            freq.set(lNum, freq.get(lNum) - 1);
            left++;
        }

        ans = Math.max(ans, right - left + 1);
    }

    return ans;
};
