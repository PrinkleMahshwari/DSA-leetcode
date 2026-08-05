/**
 * @param {number} n
 * @param {number} k
 * @param {number[][]} invocations
 * @return {number[]}
 */
var remainingMethods = function(n, k, invocations) {
    // 1. Build the adjacency list (graph)
    const graph = Array.from({ length: n }, () => []);
    for (const [from, to] of invocations) {
        graph[from].push(to);
    }

    // 2. Find all suspicious methods using DFS
    const suspicious = new Array(n).fill(false);
    
    function dfs(node) {
        suspicious[node] = true;
        for (const next of graph[node]) {
            if (!suspicious[next]) {
                dfs(next);
            }
        }
    }
    
    dfs(k);

    // 3. Check if any non-suspicious method invokes a suspicious one
    for (const [from, to] of invocations) {
        if (!suspicious[from] && suspicious[to]) {
            // Rule broken: Return all original elements (0 to n-1)
            return Array.from({ length: n }, (_, i) => i);
        }
    }

    // 4. Collect and return only the non-suspicious methods
    const result = [];
    for (let i = 0; i < n; i++) {
        if (!suspicious[i]) {
            result.push(i);
        }
    }
    
    return result;
};
