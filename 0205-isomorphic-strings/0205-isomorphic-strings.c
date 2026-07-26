#include <stdbool.h>
#include <string.h>

bool isIsomorphic(char* s, char* t) {
    int len = strlen(s);
    int mapS[256] = {0};
    int mapT[256] = {0};

    for (int i = 0; i < len; i++) {
        unsigned char charS = s[i];
        unsigned char charT = t[i];

        if (mapS[charS] != mapT[charT]) return false;

        mapS[charS] = i + 1;
        mapT[charT] = i + 1;
    }
    return true;
}
