/**
 * @param {string} num
 * @param {number} t
 * @return {string}
 */
var smallestNumber = function(num, t) {
    // Explicit initialization to prevent array stripping issues
    const PRIMES = new Array(4);
    PRIMES[0] = 2; PRIMES[1] = 3; PRIMES[2] = 5; PRIMES[3] = 7;

    function getMinDigitsNeeded(factors) {
        let f2 = factors[0], f3 = factors[1], f5 = factors[2], f7 = factors[3];
        let cnt9 = Math.floor(f3 / 2); f3 %= 2;
        let cnt8 = Math.floor(f2 / 3); f2 %= 3;
        let cnt6 = 0;
        if (f2 > 0 && f3 > 0) { cnt6 = 1; f2--; f3--; }
        let cnt4 = Math.floor(f2 / 2); f2 %= 2;
        return f7 + f5 + cnt9 + cnt8 + cnt6 + cnt4 + f3 + f2;
    }

    function fillSmallestSuffix(buffer, startIdx, endIdx, f2, f3, f5, f7) {
        let idx = endIdx;
        let cnt9 = Math.floor(f3 / 2); f3 %= 2; for (let i = 0; i < cnt9; i++) buffer[idx--] = '9';
        let cnt8 = Math.floor(f2 / 3); f2 %= 3; for (let i = 0; i < cnt8; i++) buffer[idx--] = '8';
        for (let i = 0; i < f7; i++) buffer[idx--] = '7';
        if (f2 > 0 && f3 > 0) { buffer[idx--] = '6'; f2--; f3--; }
        for (let i = 0; i < f5; i++) buffer[idx--] = '5';
        let cnt4 = Math.floor(f2 / 2); f2 %= 2; for (let i = 0; i < cnt4; i++) buffer[idx--] = '4';
        if (f3 > 0) buffer[idx--] = '3';
        if (f2 > 0) buffer[idx--] = '2';
        while (idx >= startIdx) buffer[idx--] = '1';
    }

    function subtractDigit(f, d) {
        if (d === 2) f[0] = Math.max(0, f[0] - 1);
        else if (d === 3) f[1] = Math.max(0, f[1] - 1);
        else if (d === 4) f[0] = Math.max(0, f[0] - 2);
        else if (d === 5) f[2] = Math.max(0, f[2] - 1);
        else if (d === 6) { f[0] = Math.max(0, f[0] - 1); f[1] = Math.max(0, f[1] - 1); }
        else if (d === 7) f[3] = Math.max(0, f[3] - 1);
        else if (d === 8) f[0] = Math.max(0, f[0] - 3);
        else if (d === 9) f[1] = Math.max(0, f[1] - 2);
    }

    let req = new Array(4);
    req[0] = 0; req[1] = 0; req[2] = 0; req[3] = 0;

    for (let i = 0; i < 4; i++) {
        while (t % PRIMES[i] === 0) { req[i]++; t /= PRIMES[i]; }
    }
    if (t > 1) return "-1";

    let n = num.length;
    let s = num.split('');
    let prefixStates = Array.from({ length: n + 1 }, () => {
        let arr = new Array(4);
        arr[0] = 0; arr[1] = 0; arr[2] = 0; arr[3] = 0;
        return arr;
    });
    prefixStates[0] = [...req];

    let validLen = 0;
    for (let i = 0; i < n; i++) {
        if (s[i] === '0') break;
        prefixStates[i + 1] = [...prefixStates[i]];
        subtractDigit(prefixStates[i + 1], s[i] - '0');
        validLen++;
    }

    if (validLen === n && prefixStates[n].every(x => x === 0)) return num;

    for (let pos = Math.min(n - 1, validLen); pos >= 0; pos--) {
        let baseReq = prefixStates[pos];
        let currentDigit = s[pos] - '0';
        for (let d = currentDigit + 1; d <= 9; d++) {
            let f2 = baseReq[0], f3 = baseReq[1], f5 = baseReq[2], f7 = baseReq[3];
            if (d === 2 || d === 6 || d === 4 || d === 8) f2 = Math.max(0, f2 - (d === 2 ? 1 : d === 6 ? 1 : d === 4 ? 2 : 3));
            if (d === 3 || d === 6 || d === 9) f3 = Math.max(0, f3 - (d === 3 ? 1 : d === 6 ? 1 : 2));
            if (d === 5) f5 = Math.max(0, f5 - 1);
            if (d === 7) f7 = Math.max(0, f7 - 1);

            let remLen = n - 1 - pos;
            if (getMinDigitsNeeded([f2, f3, f5, f7]) <= remLen) {
                let ans = new Array(n);
                for (let i = 0; i < pos; i++) ans[i] = s[i];
                ans[pos] = String(d);
                fillSmallestSuffix(ans, pos + 1, n - 1, f2, f3, f5, f7);
                return ans.join('');
            }
        }
    }

    let minDigitsNeeded = getMinDigitsNeeded(req);
    let targetLen = Math.max(n + 1, minDigitsNeeded);
    let ans = new Array(targetLen);
    fillSmallestSuffix(ans, 0, targetLen - 1, req[0], req[1], req[2], req[3]);
    return ans.join('');
};
