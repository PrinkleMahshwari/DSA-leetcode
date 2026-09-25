#include <stdlib.h>
#include <string.h>

typedef struct {
    char** data;
    int size;
    int capacity;
} StringSet;

void initSet(StringSet* set) {
    set->size = 0;
    set->capacity = 8;
    set->data = (char**)malloc(set->capacity * sizeof(char*));
}

int cmpStr(const void* a, const void* b) {
    return strcmp(*(const char**)a, *(const char**)b);
}

void addSet(StringSet* set, const char* str) {
    for (int i = 0; i < set->size; i++) {
        if (strcmp(set->data[i], str) == 0) return;
    }
    if (set->size >= set->capacity) {
        set->capacity *= 2;
        set->data = (char**)realloc(set->data, set->capacity * sizeof(char*));
    }
    set->data[set->size++] = strdup(str);
}

void freeSet(StringSet* set) {
    for (int i = 0; i < set->size; i++) free(set->data[i]);
    free(set->data);
}

StringSet combine(StringSet first, StringSet second) {
    StringSet result;
    initSet(&result);
    for (int i = 0; i < first.size; i++) {
        for (int j = 0; j < second.size; j++) {
            char buf[1024];
            sprintf(buf, "%s%s", first.data[i], second.data[j]);
            addSet(&result, buf);
        }
    }
    return result;
}

// Forward Declarations for Recursive Parser
StringSet parseExpression(const char* expr, int* idx);
StringSet parseTerm(const char* expr, int* idx);
StringSet parseFactor(const char* expr, int* idx);

StringSet parseExpression(const char* expr, int* idx) {
    StringSet result = parseTerm(expr, idx);
    while (expr[*idx] != '\0' && expr[*idx] == ',') {
        (*idx)++; // skip ','
        StringSet next = parseTerm(expr, idx);
        for (int i = 0; i < next.size; i++) {
            addSet(&result, next.data[i]);
        }
        freeSet(&next);
    }
    return result;
}

StringSet parseTerm(const char* expr, int* idx) {
    StringSet result;
    initSet(&result);
    addSet(&result, "");
    while (expr[*idx] != '\0' && expr[*idx] != '}' && expr[*idx] != ',') {
        StringSet next = parseFactor(expr, idx);
        StringSet updated = combine(result, next);
        freeSet(&result);
        freeSet(&next);
        result = updated;
    }
    return result;
}

StringSet parseFactor(const char* expr, int* idx) {
    StringSet result;
    initSet(&result);
    if (expr[*idx] == '{') {
        (*idx)++; // skip '{'
        freeSet(&result);
        result = parseExpression(expr, idx);
        (*idx)++; // skip '}'
        return result;
    }
    char buf[2] = {expr[*idx], '\0'};
    addSet(&result, buf);
    (*idx)++;
    return result;
}

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
char** braceExpansionII(char* expression, int* returnSize) {
    int idx = 0;
    StringSet resSet = parseExpression(expression, &idx);
    
    // Sort final result subset alphabetically
    qsort(resSet.data, resSet.size, sizeof(char*), cmpStr);
    
    char** answer = (char**)malloc(resSet.size * sizeof(char*));
    for (int i = 0; i < resSet.size; i++) {
        answer[i] = strdup(resSet.data[i]);
    }
    
    *returnSize = resSet.size;
    freeSet(&resSet);
    return answer;
}
