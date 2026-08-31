#include <stdlib.h>
#include <limits.h>

/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     struct ListNode *next;
 * };
 */
/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* nodesBetweenCriticalPoints(struct ListNode* head, int* returnSize) {
    int firstCritical = -1;
    int lastCritical = -1;

    int minDistance = INT_MAX;

    int index = 1;

    struct ListNode* prev = head;
    struct ListNode* curr = head->next;

    while (curr != NULL && curr->next != NULL) {
        struct ListNode* next = curr->next;

        // check if current node is a critical point
        if ((curr->val > prev->val && curr->val > next->val) || 
            (curr->val < prev->val && curr->val < next->val)) {

            // first critical point
            if (firstCritical == -1) {
                firstCritical = index;
            } else {
                // distance from previous critical point
                int dist = index - lastCritical;
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }

            lastCritical = index;
        }

        prev = curr;
        curr = next;
        index++;
    }

    int* result = (int*)malloc(2 * sizeof(int));
    *returnSize = 2;

    // fewer than two critical points
    if (firstCritical == lastCritical) {
        result[0] = -1;
        result[1] = -1;
        return result;
    }
    
    int maxDistance = lastCritical - firstCritical;
    result[0] = minDistance;
    result[1] = maxDistance;

    return result;
}
