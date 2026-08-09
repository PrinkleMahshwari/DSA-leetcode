/**
 * @param {number[]} piles
 * @return {number}
 */
var stoneGameII = function(piles) {
    const n = piles.length;
    
    // Create a 2D array for DP caching initialized to 0
    const dp = Array.from({ length: n }, () => new Array(n + 1).fill(0));
    
    // Build suffix sums
    const suffix = new Array(n + 1).fill(0);
    for (let i = n - 1; i >= 0; i--) {
        suffix[i] = suffix[i + 1] + piles[i];
    }
    
    function solve(i, m) {
        // Base case: Out of piles
        if (i >= n) return 0;
        
        // If current player can clear the rest of the board
        if (2 * m >= n - i) return suffix[i];
        
        // Return pre-calculated state
        if (dp[i][m] !== 0) return dp[i][m];
        
        let best = 0;
        for (let x = 1; x <= 2 * m; x++) {
            const nextM = Math.max(m, x);
            const opponent = solve(i + x, nextM);
            const current = suffix[i] - opponent;
            best = Math.max(best, current);
        }
        
        dp[i][m] = best;
        return best;
    }
    
    return solve(0, 1);
};
