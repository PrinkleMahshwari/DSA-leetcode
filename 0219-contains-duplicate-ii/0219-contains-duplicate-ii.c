#include <stdbool.h>
#include <stdlib.h>
#include <string.h>

// A fast integer hashing mixer function
unsigned int hash_code(int key, int capacity) {
    unsigned int h = (unsigned int)key;
    h ^= h >> 16;
    h *= 0x85ebca6b;
    h ^= h >> 13;
    h *= 0xc2b2ae35;
    h ^= h >> 16;
    return h % capacity;
}

bool containsNearbyDuplicate(int* nums, int numsSize, int k) {
    if (numsSize <= 1 || k <= 0) return false;

    // Bound the internal table size to preserve memory allocations
    int capacity = (k < numsSize) ? (k * 2 + 1) : (numsSize + 1);
    
    // Allocate primitive arrays: values tracking slot contents, flags tracking status
    int* table = (int*)malloc(capacity * sizeof(int));
    bool* occupied = (bool*)calloc(capacity, sizeof(bool));

    for (int i = 0; i < numsSize; i++) {
        int currentNum = nums[i];
        
        // --- 1. Find or Insert Current Number ---
        unsigned int idx = hash_code(currentNum, capacity);
        while (occupied[idx]) {
            if (table[idx] == currentNum) {
                // Duplicate found within our active window
                free(table);
                free(occupied);
                return true;
            }
            idx = (idx + 1) % capacity;
        }
        table[idx] = currentNum;
        occupied[idx] = true;

        // --- 2. Evict Outdated Window Elements (i >= k) ---
        if (i >= k) {
            int oldNum = nums[i - k];
            unsigned int delIdx = hash_code(oldNum, capacity);
            
            while (occupied[delIdx]) {
                if (table[delIdx] == oldNum) {
                    occupied[delIdx] = false; // Safely free the slot record
                    
                    // Linear Probing fix: Rehash subsequent keys in the cluster block
                    unsigned int nextIdx = (delIdx + 1) % capacity;
                    while (occupied[nextIdx]) {
                        int remNum = table[nextIdx];
                        occupied[nextIdx] = false;
                        
                        unsigned int reInsertIdx = hash_code(remNum, capacity);
                        while (occupied[reInsertIdx]) {
                            reInsertIdx = (reInsertIdx + 1) % capacity;
                        }
                        table[reInsertIdx] = remNum;
                        occupied[reInsertIdx] = true;
                        
                        nextIdx = (nextIdx + 1) % capacity;
                    }
                    break;
                }
                delIdx = (delIdx + 1) % capacity;
            }
        }
    }

    free(table);
    free(occupied);
    return false;
}
