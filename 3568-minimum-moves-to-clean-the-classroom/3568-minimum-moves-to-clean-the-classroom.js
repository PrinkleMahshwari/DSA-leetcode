class State {
    constructor(r, c, mask, energy, moves) {
        this.r = r;
        this.c = c;
        this.mask = mask;
        this.energy = energy;
        this.moves = moves;
    }
}

/**
 * @param {string[]} classroom
 * @param {number} energy
 * @return {number}
 */
var minMoves = function(classroom, energy) {
    const m = classroom.length;
    const n = classroom[0].length;

    let startR = 0, startC = 0;
    let litterCount = 0;

    // assign an index to every litter cell
    const litterId = Array.from({ length: m }, () => new Int32Array(n).fill(-1));

    for (let r = 0; r < m; r++) {
        for (let c = 0; c < n; c++) {
            const ch = classroom[r][c];
            if (ch === 'S') {
                startR = r;
                startC = c;
            } else if (ch === 'L') {
                litterId[r][c] = litterCount++;
            }
        }
    }

    // already clean
    if (litterCount === 0) return 0;

    const fullMask = (1 << litterCount) - 1;
    const numStates = 1 << litterCount;

    const bestEnergy = Array.from({ length: m }, () => 
        Array.from({ length: n }, () => new Int32Array(numStates).fill(-1))
    );

    // Standard high-speed pointer indexing for Queue mechanics
    const queue = [];
    let head = 0;

    queue.push(new State(startR, startC, 0, energy, 0));
    bestEnergy[startR][startC][0] = energy;

    const dr = [-1, 1, 0, 0];
    const dc = [0, 0, -1, 1];

    while (head < queue.length) {
        const current = queue[head++];
        const { r, c, mask, energy: e, moves } = current;

        if (mask === fullMask) return moves;

        for (let d = 0; d < 4; d++) {
            const nr = r + dr[d];
            const nc = c + dc[d];

            // outside grid
            if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
            // obstacle
            if (classroom[nr][nc] === 'X') continue;
            // no energy to make this move
            if (e === 0) continue;

            let newEnergy = e - 1;
            let newMask = mask;
            const cell = classroom[nr][nc];

            // collect litter
            if (cell === 'L') {
                const id = litterId[nr][nc];
                newMask |= (1 << id);
            }

            // reset energy
            if (cell === 'R') {
                newEnergy = energy;
            }

            if (newEnergy <= bestEnergy[nr][nc][newMask]) continue;

            bestEnergy[nr][nc][newMask] = newEnergy;
            queue.push(new State(nr, nc, newMask, newEnergy, moves + 1));
        }
    }

    return -1;
};
