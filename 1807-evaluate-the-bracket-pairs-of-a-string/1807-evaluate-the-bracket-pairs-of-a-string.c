#include <stdlib.h>
#include <string.h>

// Lightweight structure for hash map buckets
typedef struct HashNode {
    char* key;
    char* value;
    struct HashNode* next;
} HashNode;

#define HASH_SIZE 10007

unsigned int get_hash(const char* str) {
    unsigned int hash = 5381;
    int c;
    while ((c = *str++)) {
        hash = ((hash << 5) + hash) + c;
    }
    return hash % HASH_SIZE;
}

void insert_map(HashNode** table, const char* key, const char* value) {
    unsigned int idx = get_hash(key);
    HashNode* newNode = (HashNode*)malloc(sizeof(HashNode));
    newNode->key = strdup(key);
    newNode->value = strdup(value);
    newNode->next = table[idx];
    table[idx] = newNode;
}

const char* search_map(HashNode** table, const char* key) {
    unsigned int idx = get_hash(key);
    HashNode* curr = table[idx];
    while (curr != NULL) {
        if (strcmp(curr->key, key) == 0) {
            return curr->value;
        }
        curr = curr->next;
    }
    return "?";
}

char* evaluate(char* s, char*** knowledge, int knowledgeSize, int* knowledgeColSize) {
    HashNode** table = (HashNode**)calloc(HASH_SIZE, sizeof(HashNode*));
    for (int i = 0; i < knowledgeSize; i++) {
        insert_map(table, knowledge[i][0], knowledge[i][1]);
    }

    int s_len = strlen(s);
    
    // First Pass: Calculate exact target length required for memory allocation
    int target_len = 0;
    int i = 0;
    while (i < s_len) {
        if (s[i] != '(') {
            target_len++;
            i++;
            continue;
        }
        int j = i + 1;
        while (s[j] != ')') j++;
        
        int key_len = j - i - 1;
        char* key = (char*)malloc((key_len + 1) * sizeof(char));
        strncpy(key, s + i + 1, key_len);
        key[key_len] = '\0';
        
        target_len += strlen(search_map(table, key));
        free(key);
        i = j + 1;
    }

    // Allocate exact final string space
    char* result = (char*)malloc((target_len + 1) * sizeof(char));
    int res_p = 0;
    i = 0;

    // Second Pass: Fill string data
    while (i < s_len) {
        if (s[i] != '(') {
            result[res_p++] = s[i++];
            continue;
        }
        int j = i + 1;
        while (s[j] != ')') j++;
        
        int key_len = j - i - 1;
        char* key = (char*)malloc((key_len + 1) * sizeof(char));
        strncpy(key, s + i + 1, key_len);
        key[key_len] = '\0';
        
        const char* val = search_map(table, key);
        int val_len = strlen(val);
        strcpy(result + res_p, val);
        res_p += val_len;
        
        free(key);
        i = j + 1;
    }
    result[res_p] = '\0';

    // FIX: Corrected loop bounds to use 'k < HASH_SIZE' safely
    for (int k = 0; k < HASH_SIZE; k++) {
        HashNode* curr = table[k];
        while (curr != NULL) {
            HashNode* tmp = curr;
            curr = curr->next;
            free(tmp->key);
            free(tmp->value);
            free(tmp);
        }
    }
    free(table);

    return result;
}
