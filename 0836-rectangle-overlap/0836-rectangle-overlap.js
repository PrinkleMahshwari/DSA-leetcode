/**
 * @param {number[]} rec1
 * @param {number[]} rec2
 * @return {boolean}
 */
var isRectangleOverlap = function(rec1, rec2) {
    const overlapWidth = Math.min(rec1[2], rec2[2]) - Math.max(rec1[0], rec2[0]);
    const overlapHeight = Math.min(rec1[3], rec2[3]) - Math.max(rec1[1], rec2[1]);

    return overlapWidth > 0 && overlapHeight > 0;
};
