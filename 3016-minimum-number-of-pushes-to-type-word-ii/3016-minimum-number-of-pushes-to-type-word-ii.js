/**
 * @param {string} word
 * @return {number}
 */
var minimumPushes = function(word) {
    // 26-slot typed array allocated instantly on the stack
    const freq = new Int32Array(26);
    const len = word.length;
    
    // Direct character reading via charCodeAt to bypass array allocations
    for (let i = 0; i < len; i++) {
        freq[word.charCodeAt(i) - 97]++;
    }
    
    // Native fast sort
    freq.sort();
    
    let totalPushes = 0;
    let keyIndex = 0;
    
    // Scan backward from the highest frequencies
    for (let i = 25; i >= 0; i--) {
        if (freq[i] === 0) {
            break; // Stop immediately once all unique letters are processed
        }
        
        const pushCost = Math.floor(keyIndex / 8) + 1;
        totalPushes += freq[i] * pushCost;
        keyIndex++;
    }
    
    return totalPushes;
};
