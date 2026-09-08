/**
 * @param {number} n
 * @return {number}
 */
var countCommas = function(n) {
    return (n < 1000) ? 0 : n - 999;
};