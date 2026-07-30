/**
 * @param {string} word
 * @return {number}
 */
var minimumPushes = function(word) {
    // Step 1: Count character frequencies
    const counts = new Int32Array(26);
    for (let i = 0; i < word.length; i++) {
        counts[word.charCodeAt(i) - 97]++;
    }
    
    // Step 2: Sort frequencies in descending order
    counts.sort().reverse();
    
    // Step 3: Compute optimal pushes greedily based on 8-key mapping tiers
    let totalPushes = 0;
    for (let i = 0; i < 26; i++) {
        if (counts[i] === 0) break; // Optimization: stop early if no more characters
        totalPushes += counts[i] * (Math.floor(i / 8) + 1);
    }
    
    return totalPushes;
};
