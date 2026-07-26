#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

#define HASH_SIZE 1009

typedef struct HashNode {
    char* word;
    int lastSeen;
    struct HashNode* next;
} HashNode;

unsigned int get_hash(char* str) {
    unsigned int hash = 5381;
    while (*str) hash = ((hash << 5) + hash) + *str++;
    return hash % HASH_SIZE;
}

bool wordPattern(char* pattern, char* s) {
    int pLen = strlen(pattern);
    int charLastSeen[26] = {0};
    HashNode* hashTable[HASH_SIZE] = {NULL};

    // Duplicate string s to safely tokenize it with strtok
    char* sCopy = strdup(s);
    char* token = strtok(sCopy, " ");
    int i = 0;

    while (token != NULL) {
        if (i >= pLen) { free(sCopy); return false; }

        int charIdx = pattern[i] - 'a';
        unsigned int h = get_hash(token);
        
        // Find if word was seen previously
        HashNode* curr = hashTable[h];
        int lastWordPos = 0;
        while (curr != NULL) {
            if (strcmp(curr->word, token) == 0) {
                lastWordPos = curr->lastSeen;
                break;
            }
            curr = curr->next;
        }

        if (charLastSeen[charIdx] != lastWordPos) { free(sCopy); return false; }

        charLastSeen[charIdx] = i + 1;
        
        if (lastWordPos == 0) { // Insert new node
            HashNode* newNode = (HashNode*)malloc(sizeof(HashNode));
            newNode->word = token;
            newNode->lastSeen = i + 1;
            newNode->next = hashTable[h];
            hashTable[h] = newNode;
        } else {
            curr->lastSeen = i + 1;
        }

        token = strtok(NULL, " ");
        i++;
    }

    free(sCopy);
    return i == pLen; // Ensure count of words matches length of pattern exactly
}
