/**
 * @param {number} n
 * @param {number[][]} reservedSeats
 * @return {number}
 */
var maxNumberOfFamilies = function(n, reservedSeats) {
    // Map to store reservation bitmask for each affected row
    const rows = new Map();

    for (let i = 0; i < reservedSeats.length; i++) {
        const row = reservedSeats[i][0];
        const col = reservedSeats[i][1];

        rows.set(row, (rows.get(row) || 0) | (1 << col));
    }

    // All rows without reservations can fit 2 groups
    let answer = (n - rows.size) * 2;

    // Mask for possible seating combinations
    const left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);
    const middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);
    const right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

    for (const mask of rows.values()) {
        const canLeft = (mask & left) === 0;
        const canMiddle = (mask & middle) === 0;
        const canRight = (mask & right) === 0;

        if (canLeft && canRight) {
            answer += 2;
        } else if (canLeft || canMiddle || canRight) {
            answer += 1;
        }
    }

    return answer;
};
