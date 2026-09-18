/**
 * @param {string} s
 * @return {string[]}
 */
var maxNumOfSubstrings = function(s) {
    const n = s.length;

    const first = new Array(26).fill(n);
    const last = new Array(26).fill(-1);

    // Find first and last occurrence of every character
    for (let i = 0; i < n; i++) {
        const c = s.charCodeAt(i) - 97; // 97 is the ASCII code for 'a'
        if (first[c] === n) first[c] = i;
        last[c] = i;
    }

    const intervals = [];

    // Build every valid minimal interval
    for (let c = 0; c < 26; c++) {
        if (last[c] === -1) continue;

        let left = first[c];
        let right = last[c];
        let valid = true;

        for (let i = left; i <= right; i++) {
            const current = s.charCodeAt(i) - 97;

            // This character appeared before our left boundary.
            if (first[current] < left) {
                valid = false;
                break;
            }

            // Need to include all occurrences of this character.
            right = Math.max(right, last[current]);
        }

        if (valid) {
            intervals.push([left, right]);
        }
    }

    // Sort by ending position
    intervals.sort((a, b) => a[1] - b[1]);

    const answer = [];
    let end = -1;

    for (const [left, right] of intervals) {
        if (left > end) {
            answer.push(s.substring(left, right + 1));
            end = right;
        }
    }

    return answer;
};
