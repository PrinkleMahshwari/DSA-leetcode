/**
 * @param {number[]} nums1
 * @return {boolean}
 */
var uniformArray = function(nums1) {
    let smallestOdd = Infinity;
    let smallestEven = Infinity;

    for (let i = 0; i < nums1.length; i++) {
        const num = nums1[i];
        if ((num & 1) === 0) {
            smallestEven = Math.min(smallestEven, num);
        } else {
            smallestOdd = Math.min(smallestOdd, num);
        }
    }

    // all elements already have the same parity
    if (smallestOdd === Infinity || smallestEven === Infinity) {
        return true;
    }
    
    // mixed parity: make everything odd
    return smallestOdd < smallestEven;
};
