/**
 * @param {number[]} nums
 * @return {number}
 */
var minSwaps = function(nums) {
    const n = nums.length;

    // Helper function to calculate digit sum
    const digitSum = (num) => {
        let sum = 0;
        while (num > 0) {
            sum += num % 10;
            num = Math.floor(num / 10);
        }
        return sum;
    };

    // Store each number with its index and digit sum to handle duplicate values correctly
    const arr = [];
    for (let i = 0; i < n; i++) {
        arr.push({ val: nums[i], dSum: digitSum(nums[i]), originalIdx: i });
    }

    // Sort by digit sum, then by value, then by original index to stabilize
    arr.sort((a, b) => {
        if (a.dSum !== b.dSum) return a.dSum - b.dSum;
        return a.val - b.val;
    });

    // Build the permutation of target indices directly from the sorted array mapping
    const permutation = new Array(n);
    for (let i = 0; i < n; i++) {
        permutation[arr[i].originalIdx] = i;
    }

    // Count permutation cycles
    const visited = new Array(n).fill(false);
    let cycles = 0;

    for (let i = 0; i < n; i++) {
        if (visited[i]) continue;
        cycles++;

        let current = i;
        while (!visited[current]) {
            visited[current] = true;
            current = permutation[current];
        }
    }

    return n - cycles;
};
