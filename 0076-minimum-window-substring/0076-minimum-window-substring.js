function minWindow(s, t) {
    if (!s || !t || s.length < t.length) return "";

    // Fixed Int32 array to track ASCII character counts efficiently
    const targetCounts = new Int32Array(128);
    const windowCounts = new Int32Array(128);

    let requiredMatches = 0;
    for (let i = 0; i < t.length; i++) {
        const ch = t.charCodeAt(i);
        if (targetCounts[ch] === 0) requiredMatches++;
        targetCounts[ch]++;
    }

    let left = 0, right = 0, formedMatches = 0;
    let minLen = Infinity, minLeftStart = 0;

    while (right < s.length) {
        const rightChar = s.charCodeAt(right);
        windowCounts[rightChar]++;

        if (targetCounts[rightChar] > 0 && windowCounts[rightChar] === targetCounts[rightChar]) {
            formedMatches++;
        }

        while (formedMatches === requiredMatches) {
            const currentLen = right - left + 1;
            if (currentLen < minLen) {
                minLen = currentLen;
                minLeftStart = left;
            }

            const leftChar = s.charCodeAt(left);
            windowCounts[leftChar]--;

            if (targetCounts[leftChar] > 0 && windowCounts[leftChar] < targetCounts[leftChar]) {
                formedMatches--;
            }
            left++;
        }
        right++;
    }

    return minLen === Infinity ? "" : s.substring(minLeftStart, minLeftStart + minLen);
}
