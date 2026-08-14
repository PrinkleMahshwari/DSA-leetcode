/**
 * @param {string} s
 * @return {number}
 */
var maximumLengthSubstring = function(s) {
    // Frequency array for 26 lowercase English letters
    const freq = new Array(26).fill(0);

    let left = 0;
    let maxLength = 0;

    for (let right = 0; right < s.length; right++) {
        const current = s.charCodeAt(right) - 97; // get index of current character at right ('a' is 97)
        freq[current]++; // store occurrence of that character

        // check occurrence of current character is more than 2 times or not
        while (freq[current] > 2) {
            const removed = s.charCodeAt(left) - 97; // get the index of character at left
            freq[removed]--; // remove character at left
            left++; // shrink the window from left
        }

        // update maxlength
        maxLength = Math.max(maxLength, right - left + 1);
    }

    return maxLength;
};
