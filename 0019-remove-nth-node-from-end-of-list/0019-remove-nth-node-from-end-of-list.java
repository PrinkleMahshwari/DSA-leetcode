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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        
        // dummy node handles edge case (deleting head)
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // move fast n+1 steps ahead to maintain proper gap
        for (int i = 0; i <= n; i++)
            fast = fast.next;
        
        // move both pointers
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // safe deletion
        slow.next = slow.next.next;

        return dummy.next;
    }
}