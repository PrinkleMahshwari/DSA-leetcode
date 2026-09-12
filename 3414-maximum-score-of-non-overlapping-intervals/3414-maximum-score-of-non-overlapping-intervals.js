/**
 * @param {number[][]} intervals
 * @return {number[]}
 */
var maximumWeight = function(intervals) {
    const n = intervals.length;

    // [left, right, weight, originalIndex]
    const a = new Array(n);
    for (let i = 0; i < n; i++) {
        a[i] = [intervals[i][0], intervals[i][1], intervals[i][2], i];
    }

    // Sort by right endpoint
    a.sort((x, y) => x[1] - y[1]);

    // prev[i] = last interval ending before a[i] starts
    const prev = new Array(n);
    for (let i = 0; i < n; i++) {
        let lo = 0;
        let hi = i - 1;
        let pos = -1;

        while (lo <= hi) {
            const mid = Math.floor(lo + (hi - lo) / 2);
            if (a[mid][1] < a[i][0]) {
                pos = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        prev[i] = pos;
    }

    // dp state management structure
    const createState = (score, ids) => ({ score, ids });

    const dp = new Array(5);
    for (let k = 0; k <= 4; k++) {
        dp[k] = new Array(n + 1);
    }

    for (let i = 0; i <= n; i++) dp[0][i] = createState(0, []);
    for (let k = 1; k <= 4; k++) dp[k][0] = createState(0, []);

    // Add value into a sorted array
    function add(ids, value) {
        const result = [];
        let i = 0;
        while (i < ids.length && ids[i] < value) {
            result.push(ids[i]);
            i++;
        }
        result.push(value);
        while (i < ids.length) {
            result.push(ids[i]);
            i++;
        }
        return result;
    }

    // Lexicographical array comparison
    function compare(arr1, arr2) {
        const minLen = Math.min(arr1.length, arr2.length);
        for (let i = 0; i < minLen; i++) {
            if (arr1[i] !== arr2[i]) return arr1[i] - arr2[i];
        }
        return arr1.length - arr2.length;
    }

    // Determine better state
    function better(stateA, stateB) {
        if (stateA.score !== stateB.score) {
            return stateA.score > stateB.score ? stateA : stateB;
        }
        return compare(stateA.ids, stateB.ids) <= 0 ? stateA : stateB;
    }

    for (let k = 1; k <= 4; k++) {
        for (let i = 1; i <= n; i++) {
            const cur = i - 1;
            const skip = dp[k][i - 1];
            const base = dp[k - 1][prev[cur] + 1];

            const ids = add(base.ids, a[cur][3]);
            const take = createState(base.score + a[cur][2], ids);

            dp[k][i] = better(skip, take);
        }
    }

    return dp[4][n].ids;
};
