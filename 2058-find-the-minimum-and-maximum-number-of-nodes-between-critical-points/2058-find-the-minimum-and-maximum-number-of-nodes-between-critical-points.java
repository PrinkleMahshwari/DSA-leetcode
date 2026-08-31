/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int firstCritical = -1;
        int lastCritical = -1;

        int minDistance = Integer.MAX_VALUE;

        int index = 1;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr != null && curr.next != null) {

            ListNode next = curr.next;

            // check if current nde is a critical point
            if ((curr.val > prev.val && curr.val > next.val) || 
                (curr.val < prev.val && curr.val < next.val)) {

                // first critical point
                if (firstCritical == -1) {
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
        if (firstCritical == lastCritical)
            return new int[]{-1, -1};
        
        int maxDistance = lastCritical - firstCritical;

        return new int[]{minDistance, maxDistance};
    }
}