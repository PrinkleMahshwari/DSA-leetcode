/**
 * @param {number[]} nums
 * @param {number} k
 * @return {number[]}
 */
var resultArray = function(nums, k) {
    let answer = new Array(k).fill(0n);
    let dp = new Array(k).fill(0n);

    for (const num of nums) {
        const value = num % k;
        const next = new Array(k).fill(0n);

        // start a new subarray with nums[i]
        next[value]++;

        // extend every subarray ending at the previous index
        for (let r = 0; r < k; r++) {
            if (dp[r] > 0n) {
                const newRemainder = (r * value) % k;
                next[newRemainder] += dp[r];
            }
        }

        // add all subarrays ending at this index
        for (let r = 0; r < k; r++) {
            answer[r] += next[r];
        }
        
        dp = next;
    }

    // Convert BigInt back to standard JavaScript numbers for the output format
    return answer.map(val => Number(val));
};
