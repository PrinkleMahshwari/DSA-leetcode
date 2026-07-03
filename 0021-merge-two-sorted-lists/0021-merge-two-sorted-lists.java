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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        
        // dummy node handles edge cases (empty lsits, head initilization) avoids special condition for first node insertion
        ListNode dummy = new ListNode(0);

        // tail pointer always points to last node in merged list
        ListNode tail = dummy;

        // traverse both lists while both have remaining nodes
        while (list1 != null && list2 != null) {

            // choose the smaller value node to maintain sorted order
            if (list1.val <= list2.val) {

                // attach list1 node to merged list
                tail.next = list1;

                // moves list1 forward
                list1 = list1.next;
            }
            else {

                // attach list2 node to merged list
                tail.next = list2;

                // move list2 forward
                list2 = list2.next;
            }

            // moves tail forward after attaching node
            tail = tail.next;
        }

        // attach remaining nodes (already sorted, no comparison needed)
        if (list1 != null)
            tail.next = list1;
        
        else
            tail.next = list2;
        
        // return head of merged sorted list
        return dummy.next;
    }
}