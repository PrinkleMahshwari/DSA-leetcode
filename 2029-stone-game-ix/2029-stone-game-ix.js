/**
 * @param {number[]} stones
 * @return {boolean}
 */
var stoneGameIX = function(stones) {
    const count = new Array(3).fill(0);

    for (let i = 0; i < stones.length; i++) {
        count[stones[i] % 3]++;
    }
    
    // if there are no stones with remainder 1 or 2,
    // Alice can't avoid eventually losing
    if (count[1] === 0 && count[2] === 0) {
        return false;
    }
    
    // if count[0] is even, the winner depends on 
    // whether both remainder groups exist
    if (count[0] % 2 === 0) {
        return count[1] > 0 && count[2] > 0;
    }
    
    // count[0] is odd
    // Alice wins if one of the two remainder groups
    // has at least 2 more stones than other 
    return Math.abs(count[1] - count[2]) > 2;
};
