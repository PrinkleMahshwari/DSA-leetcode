#include <stdlib.h>
#include <stdbool.h>

// Helper structure for adjacency list
typedef struct Node {
    int val;
    struct Node* next;
} Node;

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* remainingMethods(int n, int k, int** invocations, int invocationsSize, int* invocationsColSize, int* returnSize) {
    // 1. Build graph using an adjacency list
    Node** graph = (Node**)calloc(n, sizeof(Node*));
    for (int i = 0; i < invocationsSize; i++) {
        int u = invocations[i][0];
        int v = invocations[i][1];
        Node* newNode = (Node*)malloc(sizeof(Node));
        newNode->val = v;
        newNode->next = graph[u];
        graph[u] = newNode;
    }

    // 2. Find suspicious methods using an iterative BFS (Prevents Stack Overflow)
    bool* suspicious = (bool*)calloc(n, sizeof(bool));
    int* queue = (int*)malloc(n * sizeof(int));
    int head = 0, tail = 0;

    suspicious[k] = true;
    queue[tail++] = k;

    while (head < tail) {
        int currNode = queue[head++];
        Node* neighbor = graph[currNode];
        while (neighbor != NULL) {
            if (!suspicious[neighbor->val]) {
                suspicious[neighbor->val] = true;
                queue[tail++] = neighbor->val;
            }
            neighbor = neighbor->next;
        }
    }
    free(queue); // Queue is no longer needed

    // 3. Check if any non-suspicious method calls a suspicious method
    bool cannotRemove = false;
    for (int i = 0; i < invocationsSize; i++) {
        int u = invocations[i][0];
        int v = invocations[i][1];
        if (!suspicious[u] && suspicious[v]) {
            cannotRemove = true;
            break;
        }
    }

    // 4. Construct the result array based on validation
    int* result;
    if (cannotRemove) {
        *returnSize = n;
        result = (int*)malloc(n * sizeof(int));
        for (int i = 0; i < n; i++) {
            result[i] = i;
        }
    } else {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) count++;
        }
        *returnSize = count;
        result = (int*)malloc(count * sizeof(int));
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                result[idx++] = i;
            }
        }
    }

    // 5. Free graph memory to avoid memory leaks
    for (int i = 0; i < n; i++) {
        Node* curr = graph[i];
        while (curr != NULL) {
            Node* temp = curr;
            curr = curr->next;
            free(temp);
        }
    }
    free(graph);
    free(suspicious);

    return result;
}
