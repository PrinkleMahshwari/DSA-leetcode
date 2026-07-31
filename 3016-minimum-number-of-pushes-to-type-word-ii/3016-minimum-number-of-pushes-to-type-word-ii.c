#include <string.h>
#include <stdlib.h>

// Simple comparison logic for sorting integers in ascending order
int compare(const void* a, const void* b) {
    return (*(int*)a - *(int*)b);
}

int minimumPushes(char* word) {
    int freq[26] = {0};
    
    // Directly scan characters using string pointer offsets
    for (int i = 0; word[i] != '\0'; i++) {
        freq[word[i] - 'a']++;
    }
    
    // Fast sort exactly 26 primitive boundaries
    qsort(freq, 26, sizeof(int), compare);
    
    int totalPushes = 0;
    int keyIndex = 0;
    
    for (int i = 25; i >= 0; i--) {
        if (freq[i] == 0) {
            break;
        }
        
        int pushCost = (keyIndex / 8) + 1;
        totalPushes += freq[i] * pushCost;
        keyIndex++;
    }
    
    return totalPushes;
}
