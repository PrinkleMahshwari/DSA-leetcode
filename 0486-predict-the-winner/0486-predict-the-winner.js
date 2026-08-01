/**
 * @param {number[]} nums
 * @return {boolean}
 */
var predictTheWinner = function(nums) {
    const n = nums.length;
    // Create a 2D DP array using flat sub-arrays
    const dp = Array.from({ length: n }, () => new Int32Array(n));

    // Base case: single element subarray
    for (let i = 0; i < n; i++) {
        dp[i][i] = nums[i];
    }

    // Filling dp table for increasing subarray lengths
    for (let len = 2; len <= n; len++) {
        for (let i = 0; i <= n - len; i++) {
            const j = i + len - 1;
            
            // Replicate the relative minimax subtraction
            dp[i][j] = Math.max(
                nums[i] - dp[i + 1][j], // Picking left end
                nums[j] - dp[i][j - 1]  // Picking right end
            );
        }
    }

    return dp[0][n - 1] >= 0;
};
