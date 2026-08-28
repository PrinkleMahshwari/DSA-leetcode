/**
 * @param {string} s
 * @param {string} target
 * @return {string}
 */
var lexPalindromicPermutation = function(s, target) {
    const n = s.length;

    // Count characters
    const freq = new Int32Array(26);
    for (let i = 0; i < n; i++) {
        freq[s.charCodeAt(i) - 97]++;
    }

    // Check if a palindrome is possible
    let odd = 0;
    let middle = -1;
    for (let i = 0; i < 26; i++) {
        if ((freq[i] & 1) === 1) {
            odd++;
            middle = i;
        }
    }

    if (odd > 1) {
        return "";
    }

    const halfLen = Math.floor(n / 2);

    /* Special case: n = 1 */
    if (halfLen === 0) {
        const only = String.fromCharCode(97 + middle);
        return only > target[0] ? only : "";
    }

    // Frequency of characters available for the first half
    const halfFreq = new Int32Array(26);
    for (let i = 0; i < 26; i++) {
        halfFreq[i] = Math.floor(freq[i] / 2);
    }

    /* target's first half */
    const targetHalf = target.substring(0, halfLen);

    /* First possibility: Try to construct exactly targetHalf */
    const remaining = new Int32Array(halfFreq);
    let canMatch = true;

    for (let i = 0; i < halfLen; i++) {
        const c = targetHalf.charCodeAt(i) - 97;
        if (remaining[c] === 0) {
            canMatch = false;
            break;
        }
        remaining[c]--;
    }

    if (canMatch) {
        const half = targetHalf.split('');
        const candidate = buildPalindrome(half, middle, n);
        if (candidate > target) {
            return candidate;
        }
    }

    /* Second possibility: Find the smallest half strictly greater than targetHalf */
    const nextHalf = findNextHalf(targetHalf, halfFreq);
    if (nextHalf === null) {
        return "";
    }

    return buildPalindrome(nextHalf, middle, n);

    function findNextHalf(targetStr, originalFreq) {
        const len = targetStr.length;
        if (len === 0) return null;

        const freqCopy = new Int32Array(originalFreq);

        /* Match target from left to right as much as possible */
        let matched = 0;
        while (matched < len) {
            const c = targetStr.charCodeAt(matched) - 97;
            if (freqCopy[c] === 0) {
                break;
            }
            freqCopy[c]--;
            matched++;
        }

        let pivot = matched - 1;
        if (matched < len) {
            pivot = matched;
        }

        /* Try every possible pivot from right to left */
        for (let p = pivot; p >= 0; p--) {
            if (p < matched) {
                const restored = targetStr.charCodeAt(p) - 97;
                freqCopy[restored]++;
            }

            const wanted = targetStr.charCodeAt(p) - 97;

            /* Find the smallest available character strictly greater than target[p] */
            for (let c = wanted + 1; c < 26; c++) {
                if (freqCopy[c] === 0) continue;

                const result = new Array(len);

                /* Prefix before pivot stays equal to target */
                for (let i = 0; i < p; i++) {
                    result[i] = targetStr[i];
                }

                /* Increase at pivot */
                result[p] = String.fromCharCode(97 + c);
                freqCopy[c]--;

                /* Fill the suffix with the smallest characters */
                let pos = p + 1;
                for (let x = 0; x < 26; x++) {
                    while (freqCopy[x] > 0) {
                        result[pos++] = String.fromCharCode(97 + x);
                        freqCopy[x]--;
                    }
                }
                return result;
            }
        }
        return null;
    }

    function buildPalindrome(half, middleIdx, totalLen) {
        const res = [];
        for (let i = 0; i < half.length; i++) {
            res.push(half[i]);
        }
        if ((totalLen & 1) === 1) {
            res.push(String.fromCharCode(97 + middleIdx));
        }
        for (let i = half.length - 1; i >= 0; i--) {
            res.push(half[i]);
        }
        return res.join('');
    }
};
