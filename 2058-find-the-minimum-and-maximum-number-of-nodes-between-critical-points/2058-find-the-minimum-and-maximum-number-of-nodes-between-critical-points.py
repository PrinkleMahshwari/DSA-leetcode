# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution:
    def nodesBetweenCriticalPoints(self, head: Optional[ListNode]) -> list[int]:
        firstCritical = -1
        lastCritical = -1

        minDistance = float('inf')

        index = 1

        prev = head
        curr = head.next

        while curr is not None and curr.next is not None:
            nxt = curr.next

            # check if current node is a critical point
            if (curr.val > prev.val and curr.val > nxt.val) or \
               (curr.val < prev.val and curr.val < nxt.val):

                # first critical point
                if firstCritical == -1:
                    firstCritical = index
                else:
                    # distance from previous critical point
                    minDistance = min(minDistance, index - lastCritical)

                lastCritical = index

            prev = curr
            curr = nxt
            index += 1

        # fewer than two critical points
        if firstCritical == lastCritical:
            return [-1, -1]
        
        maxDistance = lastCritical - firstCritical

        return [minDistance, maxDistance]
