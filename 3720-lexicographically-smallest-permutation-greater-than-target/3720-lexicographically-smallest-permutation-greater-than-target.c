#include <stdlib.h>
#include <string.h>

int findGreater(int* freq, int current) {
    for (int c = current + 1; c < 26; c++) {
        if (freq[c] > 0) return c;
    }
    return -1;
}

void fillSmallest(char* ans, int start, int* freq) {
    int pos = start;
    for (int c = 0; c < 26; c++) {
        while (freq[c] > 0) {
            ans[pos++] = (char)('a' + c);
            freq[c]--;
        }
    }
}

char* lexGreaterPermutation(char* s, char* target) {
    int n = strlen(s);
    int freq[26] = {0};

    for (int j = 0; j < n; j++) {
        freq[s[j] - 'a']++;
    }

    char* ans = (char*)malloc((n + 1) * sizeof(char));
    ans[n] = '\0';
    int i = 0;

    // match target as long as possible
    while (i < n) {
        int idx = target[i] - 'a';

        if (freq[idx] > 0) {
            ans[i] = target[i];
            freq[idx]--;
            i++;
        } else {
            break;
        }
    }

    // try to make the string greater from the current pos
    if (i < n) {
        int greater = findGreater(freq, target[i] - 'a');

        if (greater != -1) {
            ans[i] = (char)('a' + greater);
            freq[greater]--;
            
            fillSmallest(ans, i + 1, freq);
            return ans;
        }
    }

    // backtrack to find the rightmost pos to increase
    i--;

    while (i >= 0) {
        int current = ans[i] - 'a';

        // put the prev matched character back
        freq[current]++;

        int greater = findGreater(freq, current);

        if (greater != -1) {
            ans[i] = (char)('a' + greater);
            freq[greater]--;

            fillSmallest(ans, i + 1, freq);
            return ans;
        }
        i--;
    }

    free(ans);
    char* empty = (char*)malloc(1 * sizeof(char));
    empty[0] = '\0';
    return empty;
}
