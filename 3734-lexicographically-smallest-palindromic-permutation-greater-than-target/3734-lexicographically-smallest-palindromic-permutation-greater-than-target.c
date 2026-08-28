#include <stdlib.h>
#include <string.h>

char* buildPalindrome(char* half, int halfLen, int middle, int n) {
    char* res = (char*)malloc((n + 1) * sizeof(char));
    int pos = 0;
    
    for (int i = 0; i < halfLen; i++) {
        res[pos++] = half[i];
    }
    if ((n & 1) == 1) {
        res[pos++] = (char)('a' + middle);
    }
    for (int i = halfLen - 1; i >= 0; i--) {
        res[pos++] = half[i];
    }
    res[pos] = '\0';
    return res;
}

char* findNextHalf(char* target, int len, int* originalFreq) {
    if (len == 0) return NULL;

    int* freq = (int*)malloc(26 * sizeof(int));
    memcpy(freq, originalFreq, 26 * sizeof(int));

    int matched = 0;
    while (matched < len) {
        int c = target[matched] - 'a';
        if (freq[c] == 0) {
            break;
        }
        freq[c]--;
        matched++;
    }

    int pivot = matched - 1;
    if (matched < len) {
        pivot = matched;
    }

    for (int p = pivot; p >= 0; p--) {
        if (p < matched) {
            int restored = target[p] - 'a';
            freq[restored]++;
        }

        int wanted = target[p] - 'a';

        for (int c = wanted + 1; c < 26; c++) {
            if (freq[c] == 0) {
                continue;
            }

            char* result = (char*)malloc((len + 1) * sizeof(char));
            result[len] = '\0';

            for (int i = 0; i < p; i++) {
                result[i] = target[i];
            }

            result[p] = (char)('a' + c);
            freq[c]--;

            int pos = p + 1;
            for (int x = 0; x < 26; x++) {
                while (freq[x] > 0) {
                    result[pos++] = (char)('a' + x);
                    freq[x]--;
                }
            }

            free(freq);
            return result;
        }
    }

    free(freq);
    return NULL;
}

char* lexPalindromicPermutation(char* s, char* target) {
    int n = strlen(s);
    int freq[26] = {0};

    for (int i = 0; i < n; i++) {
        freq[s[i] - 'a']++;
    }

    int odd = 0;
    int middle = -1;
    for (int i = 0; i < 26; i++) {
        if ((freq[i] & 1) == 1) {
            odd++;
            middle = i;
        }
    }

    if (odd > 1) {
        return "";
    }

    int halfLen = n / 2;

    if (halfLen == 0) {
        char only = (char)('a' + middle);
        if (only > target[0]) {
            char* res = (char*)malloc(2 * sizeof(char));
            res[0] = only;
            res[1] = '\0';
            return res;
        }
        return "";
    }

    int halfFreq[26];
    for (int i = 0; i < 26; i++) {
        halfFreq[i] = freq[i] / 2;
    }

    char* targetHalf = (char*)malloc((halfLen + 1) * sizeof(char));
    strncpy(targetHalf, target, halfLen);
    targetHalf[halfLen] = '\0';

    int remaining[26];
    memcpy(remaining, halfFreq, 26 * sizeof(int));
    int canMatch = 1;

    for (int i = 0; i < halfLen; i++) {
        int c = targetHalf[i] - 'a';
        if (remaining[c] == 0) {
            canMatch = 0;
            break;
        }
        remaining[c]--;
    }

    if (canMatch) {
        char* candidate = buildPalindrome(targetHalf, halfLen, middle, n);
        if (strcmp(candidate, target) > 0) {
            free(targetHalf);
            return candidate;
        }
        free(candidate);
    }

    char* nextHalf = findNextHalf(targetHalf, halfLen, halfFreq);
    free(targetHalf);

    if (nextHalf == NULL) {
        return "";
    }

    char* result = buildPalindrome(nextHalf, halfLen, middle, n);
    free(nextHalf);
    return result;
}
