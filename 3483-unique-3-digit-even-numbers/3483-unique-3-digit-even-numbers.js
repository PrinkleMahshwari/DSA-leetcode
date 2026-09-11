/**
 * @param {number[]} digits
 * @return {number}
 */
var totalNumbers = function(digits) {
    const freq = new Array(10).fill(0);

    for (const digit of digits) {
        freq[digit]++;
    }
    
    let count = 0;

    for (let num = 100; num <= 999; num++) {
        // Skip odd numbers
        if ((num & 1) !== 0) {
            continue;
        }
        
        const a = Math.floor(num / 100);
        const b = Math.floor(num / 10) % 10;
        const c = num % 10;

        const used = new Array(10).fill(0);
        used[a]++;
        used[b]++;
        used[c]++;

        let possible = true;

        for (let d = 0; d <= 9; d++) {
            if (used[d] > freq[d]) {
                possible = false;
                break;
            }
        }

        if (possible) {
            count++;
        }
    }

    return count;
};
