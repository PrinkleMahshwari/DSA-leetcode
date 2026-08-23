/**
 * @param {string} num
 * @return {boolean}
 */
var sumGame = function(num) {
    const n = num.length;
    const half = Math.floor(n / 2);

    let leftSum = 0;
    let rightSum = 0;

    let qLeft = 0;
    let qRight = 0;

    for (let i = 0; i < half; i++) {
        const c = num[i];

        if (c === '?') {
            qLeft++;
        } else {
            leftSum += c.charCodeAt(0) - 48; // '0' is 48
        }
    }

    for (let i = half; i < n; i++) {
        const c = num[i];

        if (c === '?') {
            qRight++;
        } else {
            rightSum += c.charCodeAt(0) - 48;
        }
    }

    const diff = leftSum - rightSum;

    return 2 * diff !== 9 * (qRight - qLeft);
};
