/**
 * @param {number[][]} img1
 * @param {number[][]} img2
 * @return {number}
 */
var largestOverlap = function(img1, img2) {
    const n = img1.length;
    const size = 2 * n - 1;

    // Create a 2D array of size x size filled with zeros
    const count = Array.from({ length: size }, () => new Array(size).fill(0));
    let answer = 0;

    for (let r1 = 0; r1 < n; r1++) {
        for (let c1 = 0; c1 < n; c1++) {
            if (img1[r1][c1] === 0) continue;

            for (let r2 = 0; r2 < n; r2++) {
                for (let c2 = 0; c2 < n; c2++) {
                    if (img2[r2][c2] === 0) continue;

                    const dr = r2 - r1 + n - 1;
                    const dc = c2 - c1 + n - 1;

                    count[dr][dc]++;
                    answer = Math.max(answer, count[dr][dc]);
                }
            }
        }
    }

    return answer;
};
