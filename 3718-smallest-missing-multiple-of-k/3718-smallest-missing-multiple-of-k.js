/**
 * @param {number[]} nums
 * @param {number} k
 * @return {number}
 */
var missingMultiple = function(nums, k) {
    // Add all elements to a Set for O(1) membership lookups
    const set = new Set(nums);

    let multiple = k;

    // Increment by k until we find a value missing from the set
    while (set.has(multiple)) {
        multiple += k;
    }

    return multiple;
};
