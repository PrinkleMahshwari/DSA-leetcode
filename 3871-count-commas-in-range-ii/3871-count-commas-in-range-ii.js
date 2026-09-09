/**
 * @param {number} n
 * @return {number}
 */
var countCommas = function(n) {
    let total = 0;
    let start = 1000;
    let commas = 1;

    while (start <= n) {
        let end = Math.min(n, start * 1000 - 1);

        let count = end - start + 1;
        total += count * commas;

        // JavaScript handles safe integers up to 2^53 - 1. 
        // If n can exceed 9 quadrillion, use BigInt instead.
        start *= 1000;
        commas++;
    }

    return total;
};
