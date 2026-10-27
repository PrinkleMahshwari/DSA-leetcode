/**
 * @param {number} m
 * @param {number} n
 * @return {string[]}
 */
var createGrid = function(m, n) {
    const grid = new Array(m);

    grid[0] = ".".repeat(n);

    for (let i = 1; i < m; i++) {
        grid[i] = "#".repeat(n - 1) + ".";
    }

    return grid;
};
