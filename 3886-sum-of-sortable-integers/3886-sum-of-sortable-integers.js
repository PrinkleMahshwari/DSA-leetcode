/**
 * @param {number[]} nums
 * @return {number}
 */
var sortableIntegers = function(nums) {
    const n = nums.length;
    
    // Create a sorted clone of the array
    const sorted = [...nums].sort((a, b) => a - b);
    
    let answer = 0;

    const isRotation = (start, k) => {
        // Build the KMP prefix table for the sorted block
        const lps = new Array(k).fill(0);
        let j = 0;

        for (let i = 1; i < k; i++) {
            while (j > 0 && sorted[start + i] !== sorted[start + j]) {
                j = lps[j - 1];
            }
            if (sorted[start + i] === sorted[start + j]) {
                j++;
            }
            lps[i] = j;
        }

        // Search the sorted block in two cyclic copies of nums block
        j = 0;
        for (let i = 0; i < 2 * k - 1; i++) {
            const value = nums[start + (i % k)];

            while (j > 0 && value !== sorted[start + j]) {
                j = lps[j - 1];
            }
            if (value === sorted[start + j]) {
                j++;
            }
            if (j === k) {
                return true;
            }
        }

        return false;
    };

    // Only divisors of n can be sortable
    for (let k = 1; k <= n; k++) {
        if (n % k !== 0) continue;

        let sortable = true;

        // Check every block of length k
        for (let start = 0; start < n; start += k) {
            if (!isRotation(start, k)) {
                sortable = false;
                break;
            }
        }

        if (sortable) {
            answer += k;
        }
    }

    return answer;
};
