/**
 * @param {number[]} coins
 * @param {number} k
 * @return {number}
 */
var findKthSmallest = function(coins, k) {
    let minCoin = Infinity;
    for (let i = 0; i < coins.length; i++) {
        if (coins[i] < minCoin) minCoin = coins[i];
    }

    // Use BigInt or explicit floating operations to maintain safety for large bounds
    let left = 1n;
    let right = BigInt(minCoin) * BigInt(k);

    function gcd(a, b) {
        while (b !== 0n) {
            let temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    function lcm(a, b) {
        return (a / gcd(a, b)) * b;
    }

    function count(x, index, currentLcm, selected) {
        let result = 0n;

        for (let i = index; i < coins.length; i++) {
            let newLcm = lcm(currentLcm, BigInt(coins[i]));

            if (newLcm > x) continue;

            let contribution = x / newLcm;

            if ((selected + 1) % 2 === 1) {
                result += contribution;
            } else {
                result -= contribution;
            }

            result += count(x, i + 1, newLcm, selected + 1);
        }

        return result;
    }

    while (left < right) {
        let mid = left + (right - left) / 2n;

        if (count(mid, 0, 1n, 0) >= BigInt(k)) {
            right = mid;
        } else {
            left = mid + 1n;
        }
    }

    return Number(left);
};
