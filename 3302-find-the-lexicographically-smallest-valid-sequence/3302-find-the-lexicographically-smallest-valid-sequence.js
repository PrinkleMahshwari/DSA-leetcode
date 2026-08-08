/**
 * @param {string} word1
 * @param {string} word2
 * @return {number[]}
 */
var validSequence = function(word1, word2) {
    const n = word1.length;
    const m = word2.length;

    // Memory Optimized: Size m tracking array filled manually to save memory overhead
    const lastValidW1Idx = new Array(m);
    for (let i = 0; i < m; i++) {
        lastValidW1Idx[i] = -1;
    }

    let j = m - 1;
    for (let i = n - 1; i >= 0; i--) {
        // Direct string indexing skips allocation overhead
        if (j >= 0 && word1[i] === word2[j]) {
            lastValidW1Idx[j] = i;
            j--;
        }
    }

    const answer = new Array(m);
    let w2Idx = 0;
    let changed = false;

    for (let i = 0; i < n && w2Idx < m; i++) {
        const c1 = word1[i];
        const c2 = word2[w2Idx];

        // Case 1: Identical match configuration
        if (c1 === c2) {
            answer[w2Idx] = i;
            w2Idx++;
        } 
        // Case 2: Mutation substitution condition
        else if (!changed && (w2Idx === m - 1 || (lastValidW1Idx[w2Idx + 1] !== -1 && lastValidW1Idx[w2Idx + 1] > i))) {
            answer[w2Idx] = i;
            w2Idx++;
            changed = true;
        }
    }

    if (w2Idx < m) {
        return [];
    }

    return answer;
};
