#include <stdlib.h>

// Definition for a hash table node
typedef struct HashNode {
    int key;
    int value;
    struct HashNode* next;
} HashNode;

#define HASH_SIZE 10007

// Function to find or update key in the hash table
int get_and_increment(HashNode** table, int key) {
    int idx = abs(key) % HASH_SIZE;
    HashNode* curr = table[idx];
    while (curr != NULL) {
        if (curr->key == key) {
            curr->value++;
            return curr->value;
        }
        curr = curr->next;
    }
    // Key not found, insert new node
    HashNode* newNode = (HashNode*)malloc(sizeof(HashNode));
    newNode->key = key;
    newNode->value = 1;
    newNode->next = table[idx];
    table[idx] = newNode;
    return 1;
}

// Function to get current frequency
int get_freq(HashNode** table, int key) {
    int idx = abs(key) % HASH_SIZE;
    HashNode* curr = table[idx];
    while (curr != NULL) {
        if (curr->key == key) {
            return curr->value;
        }
        curr = curr->next;
    }
    return 0;
}

// Function to decrement key frequency
void decrement(HashNode** table, int key) {
    int idx = abs(key) % HASH_SIZE;
    HashNode* curr = table[idx];
    while (curr != NULL) {
        if (curr->key == key) {
            curr->value--;
            return;
        }
        curr = curr->next;
    }
}

int maxSubarrayLength(int* nums, int numsSize, int k) {
    // Allocate and clear the hash table entries
    HashNode** freq = (HashNode**)calloc(HASH_SIZE, sizeof(HashNode*));

    int left = 0;
    int ans = 0;

    for (int right = 0; right < numsSize; right++) {
        get_and_increment(freq, nums[right]);

        // Shrink the window from the left if the current element's frequency exceeds k
        while (get_freq(freq, nums[right]) > k) {
            decrement(freq, nums[left]);
            left++;
        }

        int current_len = right - left + 1;
        if (current_len > ans) {
            ans = current_len;
        }
    }

    // Clean up allocated memory spaces to avoid memory leaks
    for (int i = 0; i < HASH_SIZE; i++) {
        HashNode* curr = freq[i];
        while (curr != NULL) {
            HashNode* temp = curr;
            curr = curr->next;
            free(temp);
        }
    }
    free(freq);

    return ans;
}
