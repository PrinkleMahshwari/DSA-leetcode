/**
 * @param {number} n
 * @param {number} t
 * @return {number}
 */
var smallestNumber = function(n, t) {
    for (let num = n; num <= n + 10; num++) {
        let product = 1;
        let temp = num;
            
        if (temp === 0) {
            product = 0;
        }
            
        while (temp > 0) {
            product *= temp % 10;
            temp = Math.floor(temp / 10);
        }
            
        if (product % t === 0) {
            return num;
        }
    }
    return -1;
};