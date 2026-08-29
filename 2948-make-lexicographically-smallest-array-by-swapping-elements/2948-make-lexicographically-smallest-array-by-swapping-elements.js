/**
 * @param {number[]} nums
 * @param {number} limit
 * @return {number[]}
 */
var lexicographicallySmallestArray = function(nums, limit) {
    const n = nums.length;

    // Store [value, original_index] pairs
    const arr = new Array(n);
    for (let i = 0; i < n; i++) {
        arr[i] = [nums[i], i];
    }

    // Sort by value ascending
    arr.sort((a, b) => a[0] - b[0]);

    const result = new Array(n);
    let start = 0;

    while (start < n) {
        let end = start;

        // Find connected component where adjacent differences are <= limit
        while (end + 1 < n && arr[end + 1][0] - arr[end][0] <= limit) {
            end++;
        }

        const size = end - start + 1;
        const indices = new Array(size);

        // Collect original indices for this component group
        for (let i = 0; i < size; i++) {
            indices[i] = arr[start + i][1];
        }

        // Process positions in ascending order
        indices.sort((a, b) => a - b);

        // Distribute values to the sorted target indices positions
        for (let i = 0; i < size; i++) {
            result[indices[i]] = arr[start + i][0];
        }

        start = end + 1;
    }

    return result;
};
