/**
 * @param {string} s
 * @param {number} k
 * @return {string}
 */
var smallestPalindrome = function(s, k) {
    const n = s.length;
    const totalCounts = new Int32Array(26);
    for (let i = 0; i < n; i++) {
        totalCounts[s.charCodeAt(i) - 97]++;
    }
    
    const halfLen = Math.floor(n / 2);
    const halfCounts = new Int32Array(26);
    let midChar = '';
    
    for (let i = 0; i < 26; i++) {
        halfCounts[i] = Math.floor(totalCounts[i] / 2);
        if (totalCounts[i] % 2 !== 0) {
            midChar = String.fromCharCode(i + 97);
        }
    }
    
    const firstHalf = new Array(halfLen);
    let remainingSlots = halfLen;
    
    // Build the first half position by position
    for (let pos = 0; pos < halfLen; pos++) {
        let found = false;
        for (let c = 0; c < 26; c++) {
            if (halfCounts[c] > 0) {
                halfCounts[c]--;
                remainingSlots--;
                
                // Calculate remaining permutations capping at k + 1 to avoid overflow
                const p = getPermutations(halfCounts, remainingSlots, k);
                
                if (p >= k) {
                    firstHalf[pos] = String.fromCharCode(c + 97);
                    found = true;
                    break; // Character locked into place
                } else {
                    k -= p;
                    halfCounts[c]++;
                    remainingSlots++;
                }
            }
        }
        if (!found) return ""; // Fewer than k permutations available
    }
    
    // If k is still greater than 0 after checking all combinations, return empty string
    if (k > 1) return "";
    
    // Construct the full mirrored palindrome string efficiently
    const halfStr = firstHalf.join('');
    const reversedHalfStr = firstHalf.reverse().join('');
    return halfStr + midChar + reversedHalfStr;
};

// Helper to calculate multinomial coefficient capped at threshold
function getPermutations(counts, total, threshold) {
    let res = 1;
    let currentTotal = 1;
    
    for (let i = 0; i < 26; i++) {
        const cnt = counts[i];
        for (let j = 1; j <= cnt; j++) {
            res = Math.floor((res * currentTotal) / j);
            currentTotal++;
            if (res > threshold) {
                return threshold + 1; // Cap to avoid integer overflow
            }
        }
    }
    return res;
}
