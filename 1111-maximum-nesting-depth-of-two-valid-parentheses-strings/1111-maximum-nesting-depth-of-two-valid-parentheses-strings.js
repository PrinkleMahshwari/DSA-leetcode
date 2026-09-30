/**
 * @param {string} seq
 * @return {number[]}
 */
var maxDepthAfterSplit = function(seq) {
    const n = seq.length;
    const answer = new Int32Array(n);

    let depth = 0;

    for (let i = 0; i < n; i++) {
        if (seq[i] === '(') {
            depth++;
            answer[i] = depth & 1;
        } else {
            answer[i] = depth & 1;
            depth--;
        }
    }

    return Array.from(answer);
};
