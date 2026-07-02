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
        
        // Two-Pointer technique (Fast & Slow) with a Dummy node
        // locate the node right before the target node in a single pass
        // by advancing a 'fast' pointer N steps ahead, the gap between 'fast' and 'slow' will be sitting perfectly behind the node we need to delete

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // step 1: advance the 'fast' pointer n+1 steps forward to build the gap
        for (int i = 0; i <= n; i++)
            fast = fast.next;
        
        // step 2: move both pointers simultaneously maintaining the gap until 'fast' drops off the end of the list
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // step 3: delete the Nth node from the end
        // skip over the target node by re-linking the next pointer
        slow.next = slow.next.next;

        // return the actual head of the modified list
        return dummy.next;
    }
}