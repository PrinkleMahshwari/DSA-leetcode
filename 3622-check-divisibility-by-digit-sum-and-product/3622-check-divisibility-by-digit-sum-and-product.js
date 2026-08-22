/**
 * @param {number} n
 * @return {boolean}
 */
var checkDivisibility = function(n) {
    const original = n;
    let sum = 0;
    let product = 1;

    while (n > 0) {
        const digit = n % 10;

        sum += digit;
        product *= digit;

        // Force integer division in JavaScript
        n = Math.floor(n / 10);
    }

    // Edge case safeguard: division by zero is handled by returning false
    if (sum + product === 0) return false;

    return original % (sum + product) === 0;
};
