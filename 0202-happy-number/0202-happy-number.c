#include <stdbool.h>

int getNext(int n) {
    int sum = 0;
    while (n > 0) {
        int digit = n % 10;
        sum += digit * digit;
        n /= 10;
    }
    return sum;
}

bool isHappy(int n) {
    // Unhappy numbers always drop below 243 and loop. 
    // We use a flat boolean tracker to mimic a HashSet safely.
    bool seen[256] = {false};

    while (n != 1) {
        // If n falls inside our hash boundary range, check for cycles
        if (n < 256) {
            if (seen[n]) return false;
            seen[n] = true;
        } else if (n == 4) { 
            // Shortcut: Any number that hits 4 will enter the unhappy loop
            return false;
        }
        
        n = getNext(n);
    }

    return true;
}
