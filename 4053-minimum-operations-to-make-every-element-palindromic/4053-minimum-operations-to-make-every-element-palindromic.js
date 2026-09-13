// Precomputed globally to run exactly once across all 806 test cases
let evens = [];
let odds = [];
let initialized = false;

function generatePalindrome(base, isOddLen) {
    let p = base;
    let temp = isOddLen ? Math.floor(base / 10) : base;
    while (temp > 0) {
        p = p * 10 + (temp % 10);
        temp = Math.floor(temp / 10);
    }
    return p;
}

function initCache() {
    if (initialized) return;

    for (let i = 1; i <= 100000; i++) {
        const p1 = generatePalindrome(i, true);
        if (p1 > 0) {
            if (p1 % 2 === 0) evens.push(p1);
            else odds.push(p1);
        }

        const p2 = generatePalindrome(i, false);
        if (p2 > 0) {
            if (p2 % 2 === 0) evens.push(p2);
            else odds.push(p2);
        }
    }

    evens.sort((a, b) => a - b);
    odds.sort((a, b) => a - b);
    initialized = true;
}

// Binary search helper for finding insertion point
function binarySearch(arr, target) {
    let lo = 0;
    let hi = arr.length - 1;
    while (lo <= hi) {
        const mid = Math.floor(lo + (hi - lo) / 2);
        if (arr[mid] === target) return mid;
        if (arr[mid] < target) lo = mid + 1;
        else hi = mid - 1;
    }
    return -lo - 1; // Negative index format matching Java
}

/**
 * @param {number[]} nums
 * @return {number}
 */
var minOperations = function(nums) {
    initCache();

    const virelqunox = nums;
    let totalOps = 0;

    for (const x of virelqunox) {
        const targetArray = (x % 2 === 0) ? evens : odds;
        const idx = binarySearch(targetArray, x);

        if (idx >= 0) continue;

        const ins = -idx - 1;
        let minDelta = Infinity;

        if (ins < targetArray.length) {
            minDelta = Math.min(minDelta, targetArray[ins] - x);
        }
        if (ins > 0) {
            minDelta = Math.min(minDelta, x - targetArray[ins - 1]);
        }

        totalOps += Math.floor(minDelta / 2);
    }

    return totalOps;
};
