/**
 * @param {number[]} nums
 * @return {number}
 */
var minimumCost = function(nums) {
    // Step 1: Sort the array to find the true median
    nums.sort((a, b) => a - b);
    const n = nums.length;
    const median = nums[Math.floor(n / 2)];

    // Step 2: Get the closest valid palindrome numbers surrounding the median
    const lowerPalindrome = getLowerPalindrome(median);
    const upperPalindrome = getUpperPalindrome(median);

    // Step 3: Compute total operational cost for both candidates
    const costWithLower = calculateCost(nums, lowerPalindrome);
    const costWithUpper = calculateCost(nums, upperPalindrome);

    // Step 4: Return the minimal absolute operation cost
    return Math.min(costWithLower, costWithUpper);
};

function getLowerPalindrome(x) {
    while (!isPalindrome(x)) {
        x--;
    }
    return x;
}

function getUpperPalindrome(x) {
    while (!isPalindrome(x)) {
        x++;
    }
    return x;
}

function isPalindrome(x) {
    const s = x.toString();
    let left = 0;
    let right = s.length - 1;
    while (left < right) {
        if (s.charAt(left) !== s.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}

function calculateCost(nums, target) {
    let cost = 0;
    for (const num of nums) {
        cost += Math.abs(num - target);
    }
    return cost;
}
