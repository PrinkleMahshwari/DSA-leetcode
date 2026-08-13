/**
 * @param {string} s
 * @param {string} queryCharacters
 * @param {number[]} queryIndices
 * @return {number[]}
 */
var longestRepeating = function(s, queryCharacters, queryIndices) {
    const n = s.length;
    const k = queryIndices.length;
    const treeSize = 4 * n;

    // Parallel typed arrays to maximize runtime performance and minimize memory layout size
    const leftChar = new Array(treeSize);
    const rightChar = new Array(treeSize);
    const prefix = new Int32Array(treeSize);
    const suffix = new Int32Array(treeSize);
    const max = new Int32Array(treeSize);
    const length = new Int32Array(treeSize);
    const leafNodeMap = new Int32Array(n);

    function build(node, left, right) {
        length[node] = right - left + 1;
        if (left === right) {
            leftChar[node] = s[left];
            rightChar[node] = s[left];
            prefix[node] = 1;
            suffix[node] = 1;
            max[node] = 1;
            leafNodeMap[left] = node;
            return;
        }

        const mid = left + Math.floor((right - left) / 2);
        const leftChild = node << 1;
        const rightChild = leftChild | 1;

        build(leftChild, left, mid);
        build(rightChild, mid + 1, right);

        leftChar[node] = leftChar[leftChild];
        rightChar[node] = rightChar[rightChild];

        const aLen = length[leftChild];
        if (prefix[leftChild] === aLen && rightChar[leftChild] === leftChar[rightChild]) {
            prefix[node] = aLen + prefix[rightChild];
        } else {
            prefix[node] = prefix[leftChild];
        }

        const bLen = length[rightChild];
        if (suffix[rightChild] === bLen && rightChar[leftChild] === leftChar[rightChild]) {
            suffix[node] = bLen + suffix[leftChild];
        } else {
            suffix[node] = suffix[rightChild];
        }

        let maxVal = max[leftChild] > max[rightChild] ? max[leftChild] : max[rightChild];
        if (rightChar[leftChild] === leftChar[rightChild]) {
            const combo = suffix[leftChild] + prefix[rightChild];
            if (combo > maxVal) maxVal = combo;
        }
        max[node] = maxVal;
    }

    build(1, 0, n - 1);

    const answer = new Array(k);
    for (let i = 0; i < k; i++) {
        let node = leafNodeMap[queryIndices[i]];
        const ch = queryCharacters[i];

        leftChar[node] = ch;
        rightChar[node] = ch;

        node >>= 1;
        while (node > 0) {
            const leftChild = node << 1;
            const rightChild = leftChild | 1;

            leftChar[node] = leftChar[leftChild];
            rightChar[node] = rightChar[rightChild];

            const aLen = length[leftChild];
            if (prefix[leftChild] === aLen && rightChar[leftChild] === leftChar[rightChild]) {
                prefix[node] = aLen + prefix[rightChild];
            } else {
                prefix[node] = prefix[leftChild];
            }

            const bLen = length[rightChild];
            if (suffix[rightChild] === bLen && rightChar[leftChild] === leftChar[rightChild]) {
                suffix[node] = bLen + suffix[leftChild];
            } else {
                suffix[node] = suffix[rightChild];
            }

            let maxVal = max[leftChild] > max[rightChild] ? max[leftChild] : max[rightChild];
            if (rightChar[leftChild] === leftChar[rightChild]) {
                const combo = suffix[leftChild] + prefix[rightChild];
                if (combo > maxVal) maxVal = combo;
            }
            max[node] = maxVal;

            node >>= 1;
        }
        answer[i] = max[1];
    }

    return answer;
};
