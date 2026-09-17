/**
 * @param {number[]} arr
 * @param {number} target
 * @return {number}
 */
var minSumOfLengths = function(arr, target) {
    const n = arr.length;
    const INF = n + 1;

    // best[i] = shortest target-sum subarray completely inside arr[0...i-1]
    const best = new Array(n + 1).fill(INF);

    let left = 0;
    let sum = 0;
    let shortest = INF;
    let answer = INF;

    for (let right = 0; right < n; right++) {
        sum += arr[right];

        while (sum > target) {
            sum -= arr[left++];
        }

        if (sum === target) {
            const length = right - left + 1;

            if (best[left] !== INF) {
                answer = Math.min(answer, best[left] + length);
            }
            shortest = Math.min(shortest, length);
        }
        best[right + 1] = shortest;
    }

    return answer === INF ? -1 : answer;
};
