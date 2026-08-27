/**
 * @param {string} s
 * @param {string} target
 * @return {string}
 */
var lexGreaterPermutation = function(s, target) {
    const n = s.length;
    const freq = new Int32Array(26);

    for (let j = 0; j < s.length; j++) {
        freq[s.charCodeAt(j) - 97]++; // 'a' is 97
    }

    const ans = new Array(n);
    let i = 0;

    // match target as long as possible
    while (i < n) {
        const idx = target.charCodeAt(i) - 97;

        if (freq[idx] > 0) {
            ans[i] = target[i];
            freq[idx]--;
            i++;
        } else {
            break;
        }
    }

    // try to make the string greater from the current pos
    if (i < n) {
        const greater = findGreater(freq, target.charCodeAt(i) - 97);

        if (greater !== -1) {
            ans[i] = String.fromCharCode(97 + greater);
            freq[greater]--;
            
            fillSmallest(ans, i + 1, freq);
            return ans.join('');
        }
    }

    // backtrack to find the rightmost pos to increase
    i--;

    while (i >= 0) {
        const current = ans[i].charCodeAt(0) - 97;

        // put the prev matched character back
        freq[current]++;

        const greater = findGreater(freq, current);

        if (greater !== -1) {
            ans[i] = String.fromCharCode(97 + greater);
            freq[greater]--;

            fillSmallest(ans, i + 1, freq);
            return ans.join('');
        }
        i--;
    }
    return "";

    // helper function for finding greater than target
    function findGreater(freq, current) {
        for (let c = current + 1; c < 26; c++) {
            if (freq[c] > 0) return c;
        }
        return -1;
    }

    // helper function for filling smallest permutation
    function fillSmallest(ans, start, freq) {
        let pos = start;
        for (let c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                ans[pos++] = String.fromCharCode(97 + c);
                freq[c]--;
            }
        }
    }
};
