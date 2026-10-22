/**
 * @param {number[]} nums
 * @return {number}
 */
var minOperations = function(nums) {
    const n = nums.length;

    if (n === 1) return 0;
    
    let indexZero = 0;
    let drops = 0;
    let rises = 0;

    for (let i = 0; i < n; i++) {
        if (nums[i] === 0) {
            indexZero = i;
        }

        const next = nums[(i + 1) % n];

        if (nums[i] > next) {
            drops++;
        }

        if (nums[i] < next) {
            rises++;
        }
    }

    if (drops === 1 && indexZero === 0) {
        return 0;
    }

    let answer = Infinity;

    // Case 1: nums is a cyclic rotation of increasing order
    if (drops === 1) {
        const rotateLeft = indexZero;
        const reverseRotateReverse = 2 + (n - indexZero) % n;

        answer = Math.min(answer, Math.min(rotateLeft, reverseRotateReverse));
    }

    // Case 2: nums is a cyclic rotation of decreasing order
    if (rises === 1) {
        const reverseIndexZero = n - 1 - indexZero;

        const reverseThenRotate = 1 + reverseIndexZero;
        const rotateThenReverse = 1 + (n - reverseIndexZero) % n;

        answer = Math.min(answer, Math.min(reverseThenRotate, rotateThenReverse));
    }

    return answer === Infinity ? -1 : answer;
};
