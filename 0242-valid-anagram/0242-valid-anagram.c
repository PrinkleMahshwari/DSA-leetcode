#include <stdbool.h>
#include <string.h>

bool isAnagram(char* s, char* t) {
    int sLen = strlen(s);
    int tLen = strlen(t);
    if (sLen != tLen) return false;

    int charCounts[26] = {0};

    for (int i = 0; i < sLen; i++) {
        charCounts[s[i] - 'a']++;
        charCounts[t[i] - 'a']--;
    }

    for (int i = 0; i < 26; i++) {
        if (charCounts[i] != 0) return false;
    }

    return true;
}
