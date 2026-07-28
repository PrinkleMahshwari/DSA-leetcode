/**
 * @param {string} s
 * @return {string}
 */
var smallestPalindrome = function(s) {
    const n = s.length;
    const counts = new Int32Array(26);
    
    // Step 1: Count character frequencies
    for (let i = 0; i < n; i++) {
        counts[s.charCodeAt(i) - 97]++;
    }
    
    // Step 2: Handle the odd midpoint element for odd lengths
    let oddCharIndex = -1;
    if (n % 2 !== 0) {
        for (let i = 0; i < 26; i++) {
            if (counts[i] % 2 !== 0) {
                oddCharIndex = i;
                counts[i]--; // Make it even for mirror building
                break;
            }
        }
    }
    
    // Construct palindrome using a pre-allocated array buffer
    const result = new Array(n);
    let left = 0;
    let right = n - 1;
    
    // Step 3: Mirror smallest characters greedily from outside in
    for (let i = 0; i < 26; i++) {
        const ch = String.fromCharCode(i + 97);
        while (counts[i] > 0) {
            result[left] = ch;
            result[right] = ch;
            left++;
            right--;
            counts[i] -= 2;
        }
    }
    
    // Step 4: Drop the odd middle anchor into place
    if (oddCharIndex !== -1) {
        result[left] = String.fromCharCode(oddCharIndex + 97);
    }
    
    return result.join('');
};
