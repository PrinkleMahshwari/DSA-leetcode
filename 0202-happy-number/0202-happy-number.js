/**
 * @param {number} n
 * @return {boolean}
 */
var isHappy = function(n) {
    const seen = new Set();

    while (n !== 1 && !seen.has(n)) {
        seen.add(n);
        n = getNextSquareSum(n);
    }

    return n === 1;
};

// Helper function to extract digits and sum their squares
function getNextSquareSum(n) {
    let sum = 0;

    while (n > 0) {
        const digit = n % 10;
        sum += digit * digit;
        n = Math.floor(n / 10); // Safe integer floor division
    }

    return sum;
}
