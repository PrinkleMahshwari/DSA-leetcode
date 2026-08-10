/**
 * @param {number} n
 * @return {boolean}
 */
var winnerSquareGame = function(n) {
    // Array allocation for DP state caching
    const dp = new Array(n + 1).fill(false);
    
    dp[0] = false;
    
    for (let i = 1; i <= n; i++) {
        for (let j = 1; j * j <= i; j++) {
            const square = j * j;
            
            if (!dp[i - square]) {
                dp[i] = true;
                break;
            }
        }
    }
    
    return dp[n];
};
