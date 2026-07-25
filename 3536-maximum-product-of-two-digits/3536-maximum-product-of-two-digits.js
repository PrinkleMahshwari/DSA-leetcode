/**
 * @param {number} n
 * @return {number}
 */
var maxProduct = function(n) {
    let max1 = 0; // tracks the largest digit
    let max2 = 0; // tracks the second largest digit

    while (n > 0) {
        let digit = n % 10; // extract the last digit
        n = Math.floor(n / 10); // remove the last digit

        if (digit > max1) {
            max2 = max1; // the old largest becomes the second largest
            max1 = digit; // update new largest
        } else if (digit > max2) {
            max2 = digit; // update second largest
        }
    }

    return max2 * max1;
};