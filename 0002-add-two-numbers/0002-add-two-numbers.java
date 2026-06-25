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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // create a dummy node to act as the starting anchor of the result list
        ListNode dummyHead = new ListNode(0);
        ListNode curr = dummyHead;
        int carry = 0;
        
        // traverse both lists until both are completely exhausted and no carry remains
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;

            // add value from first list if it has nodes remaining
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            // add value from second list if it has nodes remaining
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            // create a new carry (either 0 or 1)
            carry = sum / 10;

            // create a new node with the single digit remainder and link it
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
        }
        
        // return the actual head of the sum list, skipping the placholder dummy node
        return dummyHead.next;
    }
}