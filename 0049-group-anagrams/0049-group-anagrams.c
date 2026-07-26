#include <stdlib.h>
#include <string.h>

// Helper sorting comparison method for qsort character conversions
int cmpChar(const void* a, const void* b) {
    return (*(char*)a - *(char*)b);
}

typedef struct GroupNode {
    char* sortedKey;
    char** words;
    int wordCount;
    int capacity;
    struct GroupNode* next;
} GroupNode;

char*** groupAnagrams(char** strs, int strsSize, int* returnSize, int** returnColumnSizes) {
    GroupNode* hashTable[2003] = {NULL};
    int uniqueGroups = 0;

    for (int i = 0; i < strsSize; i++) {
        char* sortedStr = strdup(strs[i]);
        qsort(sortedStr, strlen(sortedStr), sizeof(char), cmpChar);

        unsigned int h = 0;
        for (int j = 0; sortedStr[j]; j++) h = (h * 31) + sortedStr[j];
        h %= 2003;

        GroupNode* curr = hashTable[h];
        while (curr && strcmp(curr->sortedKey, sortedStr) != 0) curr = curr->next;

        if (!curr) {
            curr = (GroupNode*)malloc(sizeof(GroupNode));
            curr->sortedKey = sortedStr;
            curr->capacity = 4;
            curr->words = (char**)malloc(curr->capacity * sizeof(char*));
            curr->wordCount = 0;
            curr->next = hashTable[h];
            hashTable[h] = curr;
            uniqueGroups++;
        } else {
            free(sortedStr);
        }

        if (curr->wordCount == curr->capacity) {
            curr->capacity *= 2;
            curr->words = (char**)realloc(curr->words, curr->capacity * sizeof(char*));
        }
        curr->words[curr->wordCount++] = strs[i];
    }

    char*** result = (char***)malloc(uniqueGroups * sizeof(char**));
    *returnColumnSizes = (int*)malloc(uniqueGroups * sizeof(int));
    *returnSize = uniqueGroups;

    int idx = 0;
    for (int i = 0; i < 2003; i++) {
        GroupNode* curr = hashTable[i];
        while (curr) {
            result[idx] = curr->words;
            (*returnColumnSizes)[idx] = curr->wordCount;
            idx++;
            GroupNode* temp = curr;
            curr = curr->next;
            free(temp->sortedKey);
            free(temp);
        }
    }
    return result;
}
