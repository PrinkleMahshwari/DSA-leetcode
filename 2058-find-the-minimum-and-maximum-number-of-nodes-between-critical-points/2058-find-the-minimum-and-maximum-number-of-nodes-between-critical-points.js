/**
 * Definition for singly-linked list.
 * function ListNode(val, next) {
 *     this.val = (val===undefined ? 0 : val)
 *     this.next = (next===undefined ? null : next)
 * }
 */
/**
 * @param {ListNode} head
 * @return {number[]}
 */
var nodesBetweenCriticalPoints = function(head) {
    let firstCritical = -1;
    let lastCritical = -1;

    let minDistance = Infinity;

    let index = 1;

    let prev = head;
    let curr = head.next;

    while (curr !== null && curr.next !== null) {
        const next = curr.next;

        // check if current node is a critical point
        if ((curr.val > prev.val && curr.val > next.val) || 
            (curr.val < prev.val && curr.val < next.val)) {

            // first critical point
            if (firstCritical === -1) {
                firstCritical = index;
            } else {
                // distance from previous critical point
                minDistance = Math.min(minDistance, index - lastCritical);
            }

            lastCritical = index;
        }

        prev = curr;
        curr = next;
        index++;
    }

    // fewer than two critical points
    if (firstCritical === lastCritical) {
        return [-1, -1];
    }
    
    const maxDistance = lastCritical - firstCritical;

    return [minDistance, maxDistance];
};
