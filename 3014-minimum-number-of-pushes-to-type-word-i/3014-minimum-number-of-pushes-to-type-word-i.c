#include <string.h>
#include <stdlib.h>

// Comparator for quick-sorting frequencies in descending order
int cmpDesc(const void* a, const void* b) {
    return (*(int*)b - *(int*)a);
}

int minimumPushes(char* word) {
    int counts[26] = {0};
    
    // Step 1: Count character frequencies
    for (int i = 0; word[i] != '\0'; i++) {
        counts[word[i] - 'a']++;
    }
    
    // Step 2: Sort the fixed 26-element array in descending order
    qsort(counts, 26, sizeof(int), cmpDesc);
    
    // Step 3: Sum the push requirements based on key availability tiers
    int totalPushes = 0;
    for (int i = 0; i < 26; i++) {
        if (counts[i] == 0) break;
        totalPushes += counts[i] * ((i / 8) + 1);
    }
    
    return totalPushes;
}
