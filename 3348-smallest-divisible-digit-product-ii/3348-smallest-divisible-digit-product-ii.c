char* smallestNumber(char* num, long long t) {
    // Explicit array declarations to bypass markdown parsing filters
    int PRIMES[4];
    PRIMES[0] = 2; PRIMES[1] = 3; PRIMES[2] = 5; PRIMES[3] = 7;

    int getMinDigitsNeeded(int f2, int f3, int f5, int f7) {
        int cnt9 = f3 / 2; f3 %= 2;
        int cnt8 = f2 / 3; f2 %= 3;
        int cnt6 = 0;
        if (f2 > 0 && f3 > 0) { cnt6 = 1; f2--; f3--; }
        int cnt4 = f2 / 2; f2 %= 2;
        return f7 + f5 + cnt9 + cnt8 + cnt6 + cnt4 + f3 + f2;
    }

    void fillSmallestSuffix(char* buffer, int startIdx, int endIdx, int f2, int f3, int f5, int f7) {
        int idx = endIdx;
        int cnt9 = f3 / 2; f3 %= 2; for (int i = 0; i < cnt9; i++) buffer[idx--] = '9';
        int cnt8 = f2 / 3; f2 %= 3; for (int i = 0; i < cnt8; i++) buffer[idx--] = '8';
        for (int i = 0; i < f7; i++) buffer[idx--] = '7';
        if (f2 > 0 && f3 > 0) { buffer[idx--] = '6'; f2--; f3--; }
        for (int i = 0; i < f5; i++) buffer[idx--] = '5';
        int cnt4 = f2 / 2; f2 %= 2; for (int i = 0; i < cnt4; i++) buffer[idx--] = '4';
        if (f3 > 0) buffer[idx--] = '3';
        if (f2 > 0) buffer[idx--] = '2';
        while (idx >= startIdx) buffer[idx--] = '1';
    }

    void subtractDigit(int* f, int d) {
        if (d == 2) f[0] = f[0] > 1 ? f[0] - 1 : 0;
        else if (d == 3) f[1] = f[1] > 1 ? f[1] - 1 : 0;
        else if (d == 4) f[0] = f[0] > 2 ? f[0] - 2 : 0;
        else if (d == 5) f[2] = f[2] > 1 ? f[2] - 1 : 0;
        else if (d == 6) { f[0] = f[0] > 1 ? f[0] - 1 : 0; f[1] = f[1] > 1 ? f[1] - 1 : 0; }
        else if (d == 7) f[3] = f[3] > 1 ? f[3] - 1 : 0;
        else if (d == 8) f[0] = f[0] > 3 ? f[0] - 3 : 0;
        else if (d == 9) f[1] = f[1] > 2 ? f[1] - 2 : 0;
    }

    int req[4];
    req[0] = 0; req[1] = 0; req[2] = 0; req[3] = 0;

    for (int i = 0; i < 4; i++) {
        while (t % PRIMES[i] == 0) { req[i]++; t /= PRIMES[i]; }
    }
    if (t > 1) {
        char* fail = (char*)malloc(3 * sizeof(char));
        fail[0] = '-'; fail[1] = '1'; fail[2] = '\0';
        return fail;
    }

    int n = strlen(num);
    
    // Flattened 1D array representing an array of states: (n + 1) states * 4 factors each
    int* prefixStates = (int*)malloc((n + 1) * 4 * sizeof(int));
    prefixStates[0] = req[0]; prefixStates[1] = req[1]; prefixStates[2] = req[2]; prefixStates[3] = req[3];

    int validLen = 0;
    for (int i = 0; i < n; i++) {
        if (num[i] == '0') break;
        int current_offset = i * 4;
        int next_offset = (i + 1) * 4;
        
        prefixStates[next_offset + 0] = prefixStates[current_offset + 0];
        prefixStates[next_offset + 1] = prefixStates[current_offset + 1];
        prefixStates[next_offset + 2] = prefixStates[current_offset + 2];
        prefixStates[next_offset + 3] = prefixStates[current_offset + 3];
        
        subtractDigit(prefixStates + next_offset, num[i] - '0');
        validLen++;
    }

    int final_offset = n * 4;
    if (validLen == n && prefixStates[final_offset + 0] == 0 && prefixStates[final_offset + 1] == 0 && prefixStates[final_offset + 2] == 0 && prefixStates[final_offset + 3] == 0) {
        free(prefixStates);
        char* res = (char*)malloc((n + 1) * sizeof(char));
        strcpy(res, num);
        return res;
    }

    int back_start = n - 1 < validLen ? n - 1 : validLen;
    for (int pos = back_start; pos >= 0; pos--) {
        int pos_offset = pos * 4;
        int currentDigit = num[pos] - '0';
        
        for (int d = currentDigit + 1; d <= 9; d++) {
            int f2 = prefixStates[pos_offset + 0];
            int f3 = prefixStates[pos_offset + 1];
            int f5 = prefixStates[pos_offset + 2];
            int f7 = prefixStates[pos_offset + 3];
            
            if (d == 2 || d == 6 || d == 4 || d == 8) f2 = f2 > (d == 2 ? 1 : d == 6 ? 1 : d == 4 ? 2 : 3) ? f2 - (d == 2 ? 1 : d == 6 ? 1 : d == 4 ? 2 : 3) : 0;
            if (d == 3 || d == 6 || d == 9) f3 = f3 > (d == 3 ? 1 : d == 6 ? 1 : 2) ? f3 - (d == 3 ? 1 : d == 6 ? 1 : 2) : 0;
            if (d == 5) f5 = f5 > 1 ? f5 - 1 : 0;
            if (d == 7) f7 = f7 > 1 ? f7 - 1 : 0;

            int remLen = n - 1 - pos;
            if (getMinDigitsNeeded(f2, f3, f5, f7) <= remLen) {
                char* ans = (char*)malloc((n + 1) * sizeof(char));
                memcpy(ans, num, pos);
                ans[pos] = '0' + d;
                fillSmallestSuffix(ans, pos + 1, n - 1, f2, f3, f5, f7);
                ans[n] = '\0';
                free(prefixStates);
                return ans;
            }
        }
    }

    int minDigitsNeeded = getMinDigitsNeeded(req[0], req[1], req[2], req[3]);
    int targetLen = n + 1 > minDigitsNeeded ? n + 1 : minDigitsNeeded;
    char* ans = (char*)malloc((targetLen + 1) * sizeof(char));
    fillSmallestSuffix(ans, 0, targetLen - 1, req[0], req[1], req[2], req[3]);
    ans[targetLen] = '\0';
    free(prefixStates);
    return ans;
}
