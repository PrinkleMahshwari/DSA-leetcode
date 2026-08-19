#include <stdlib.h>

typedef struct HashNode {
    int row;
    int mask;
    struct HashNode* next;
} HashNode;

#define HASH_SIZE 10007

// Helper to look up a row mask or update it inline
int update_row_mask(HashNode** table, int row, int col, int* distinctRowsCount) {
    int idx = row % HASH_SIZE;
    HashNode* curr = table[idx];
    
    while (curr != NULL) {
        if (curr->row == row) {
            curr->mask |= (1 << col);
            return curr->mask;
        }
        curr = curr->next;
    }
    
    // Create new node if the row is encountered for the first time
    HashNode* newNode = (HashNode*)malloc(sizeof(HashNode));
    newNode->row = row;
    newNode->mask = (1 << col);
    newNode->next = table[idx];
    table[idx] = newNode;
    (*distinctRowsCount)++;
    return newNode->mask;
}

int maxNumberOfFamilies(int n, int** reservedSeats, int reservedSeatsSize, int* reservedSeatsColSize) {
    HashNode** table = (HashNode**)calloc(HASH_SIZE, sizeof(HashNode*));
    int distinctRowsCount = 0;

    for (int i = 0; i < reservedSeatsSize; i++) {
        int row = reservedSeats[i][0];
        int col = reservedSeats[i][1];
        update_row_mask(table, row, col, &distinctRowsCount);
    }

    // All rows without reservations can fit 2 groups
    long long answer = (long long)(n - distinctRowsCount) * 2;

    int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);
    int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);
    int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

    // Iterate through the hash table buckets to evaluate active rows
    for (int i = 0; i < HASH_SIZE; i++) {
        HashNode* curr = table[i];
        while (curr != NULL) {
            int mask = curr->mask;
            int canLeft = (mask & left) == 0;
            int canMiddle = (mask & middle) == 0;
            int canRight = (mask & right) == 0;

            if (canLeft && canRight) {
                answer += 2;
            } else if (canLeft || canMiddle || canRight) {
                answer += 1;
            }
            
            HashNode* temp = curr;
            curr = curr->next;
            free(temp); // Clean up memory immediately
        }
    }
    free(table);

    return (int)answer;
}
