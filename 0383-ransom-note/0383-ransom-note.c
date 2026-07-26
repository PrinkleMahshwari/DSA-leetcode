#include <stdbool.h>
#include <string.h>

bool canConstruct(char* ransomNote, char* magazine) {
    int rLen = strlen(ransomNote);
    int mLen = strlen(magazine);
    if (rLen > mLen) return false;

    int counts[26] = {0};

    for (int i = 0; i < mLen; i++) {
        counts[magazine[i] - 'a']++;
    }

    for (int i = 0; i < rLen; i++) {
        int index = ransomNote[i] - 'a';
        counts[index]--;
        if (counts[index] < 0) return false;
    }
    return true;
}
