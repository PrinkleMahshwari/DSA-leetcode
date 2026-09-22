/**
 * @param {number[]} nums
 * @param {number} k
 * @param {number[][]} queries
 * @return {number[]}
 */
var resultArray = function(nums, k, queries) {
    const n = nums.length;
    const treeSize = 4 * n;

    // Flatten tree: node properties are represented across parallel typed arrays
    // For counts, we pack it into a 1D array of size treeSize * k
    const treeProduct = new Int32Array(treeSize);
    const treeCount = new Int32Array(treeSize * k);

    function build(node, left, right) {
        if (left === right) {
            const remainder = nums[left] % k;
            treeProduct[node] = remainder;
            treeCount[node * k + remainder] = 1;
            return;
        }

        const mid = left + Math.floor((right - left) / 2);
        const leftChild = node << 1;
        const rightChild = leftChild | 1;

        build(leftChild, left, mid);
        build(rightChild, mid + 1, right);

        merge(node, leftChild, rightChild);
    }

    function update(node, left, right, index, value) {
        if (left === right) {
            const remainder = value % k;
            // Reset count slice for this leaf node
            const baseIdx = node * k;
            for (let r = 0; r < k; r++) treeCount[baseIdx + r] = 0;
            
            treeProduct[node] = remainder;
            treeCount[baseIdx + remainder] = 1;
            return;
        }

        const mid = left + Math.floor((right - left) / 2);
        const leftChild = node << 1;
        const rightChild = leftChild | 1;

        if (index <= mid) {
            update(leftChild, left, mid, index, value);
        } else {
            update(rightChild, mid + 1, right, index, value);
        }

        merge(node, leftChild, rightChild);
    }

    function query(node, left, right, queryLeft, queryRight, outResult) {
        if (queryLeft <= left && right <= queryRight) {
            outResult.product = treeProduct[node];
            const baseIdx = node * k;
            for (let r = 0; r < k; r++) outResult.count[r] = treeCount[baseIdx + r];
            return;
        }

        const mid = left + Math.floor((right - left) / 2);
        const leftChild = node << 1;
        const rightChild = leftChild | 1;

        if (queryRight <= mid) {
            query(leftChild, left, mid, queryLeft, queryRight, outResult);
            return;
        }
        if (queryLeft > mid) {
            query(rightChild, mid + 1, right, queryLeft, queryRight, outResult);
            return;
        }

        const leftResult = { product: 0, count: new Int32Array(k) };
        const rightResult = { product: 0, count: new Int32Array(k) };

        query(leftChild, left, mid, queryLeft, queryRight, leftResult);
        query(rightChild, mid + 1, right, queryLeft, queryRight, rightResult);

        // Merge logic manually applied into outResult
        outResult.product = (leftResult.product * rightResult.product) % k;
        for (let r = 0; r < k; r++) {
            outResult.count[r] = leftResult.count[r];
        }
        for (let r = 0; r < k; r++) {
            const newRemainder = (leftResult.product * r) % k;
            outResult.count[newRemainder] += rightResult.count[r];
        }
    }

    function merge(parent, leftChild, rightChild) {
        const pBase = parent * k;
        const lBase = leftChild * k;
        const rBase = rightChild * k;

        // Reset parent counts
        for (let r = 0; r < k; r++) treeCount[pBase + r] = 0;

        treeProduct[parent] = (treeProduct[leftChild] * treeProduct[rightChild]) % k;

        // Prefixes entirely inside the left segment
        for (let r = 0; r < k; r++) {
            treeCount[pBase + r] = treeCount[lBase + r];
        }

        // Prefixes that continue from left into right
        const leftProd = treeProduct[leftChild];
        for (let r = 0; r < k; r++) {
            const newRemainder = (leftProd * r) % k;
            treeCount[pBase + newRemainder] += treeCount[rBase + r];
        }
    }

    build(1, 0, n - 1);

    const answer = new Array(queries.length);
    const tempResult = { product: 0, count: new Int32Array(k) };

    for (let q = 0; q < queries.length; q++) {
        const [index, value, start, x] = queries[q];
        update(1, 0, n - 1, index, value);
        query(1, 0, n - 1, start, n - 1, tempResult);
        answer[q] = tempResult.count[x];
    }

    return answer;
};
