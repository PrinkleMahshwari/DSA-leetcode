#include <string.h>

int maximumLengthSubstring(char* s) {
    // Frequency array for 26 lowercase English letters
    int freq[26] = {0};

    int left = 0;
    int maxLength = 0;
    int len = strlen(s);

    for (int right = 0; right < len; right++) {
        int current = s[right] - 'a'; // get index of current character at right
        freq[current]++; // store occurrence of that character

        // check occurrence of current character is more than 2 times or not
        while (freq[current] > 2) {
            int removed = s[left] - 'a'; // get the index of character at left
            freq[removed]--; // remove character at left
            left++; // shrink the window from left
        }

        // update maxlength
        int currentLength = right - left + 1;
        if (currentLength > maxLength) {
            maxLength = currentLength;
        }
    }

    return maxLength;
}
