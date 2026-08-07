import java.util.Arrays;

class Solution {
    private static final int[] PRIMES = {2, 3, 5, 7};

    // O(1) Check: Returns minimum number of digits needed to satisfy remaining factors
    private int getMinDigitsNeeded(int f2, int f3, int f5, int f7) {
        int cnt9 = f3 / 2; f3 %= 2;
        int cnt8 = f2 / 3; f2 %= 3;
        
        int cnt6 = 0;
        if (f2 > 0 && f3 > 0) {
            cnt6 = 1; f2--; f3--;
        }
        
        int cnt4 = f2 / 2; f2 %= 2;
        return f7 + f5 + cnt9 + cnt8 + cnt6 + cnt4 + f3 + f2;
    }

    // O(L) Suffix Filler: Fills the remaining space greedily with correctly sorted structural digits
    private void fillSmallestSuffix(char[] buffer, int startIdx, int endIdx, int f2, int f3, int f5, int f7) {
        int idx = endIdx;
        
        // Fill from right to left with largest values so that the final string reads sorted ascending
        int cnt9 = f3 / 2; f3 %= 2; for (int i = 0; i < cnt9; i++) buffer[idx--] = '9';
        int cnt8 = f2 / 3; f2 %= 3; for (int i = 0; i < cnt8; i++) buffer[idx--] = '8';
        for (int i = 0; i < f7; i++) buffer[idx--] = '7';
        
        // FIX: Place 6 before 5 during the backward traversal so 5 appears to the left of 6
        if (f2 > 0 && f3 > 0) { buffer[idx--] = '6'; f2--; f3--; }
        for (int i = 0; i < f5; i++) buffer[idx--] = '5';
        
        int cnt4 = f2 / 2; f2 %= 2; for (int i = 0; i < cnt4; i++) buffer[idx--] = '4';
        if (f3 > 0) buffer[idx--] = '3';
        if (f2 > 0) buffer[idx--] = '2';
        
        // Pad the remaining higher-value left spaces with '1's
        while (idx >= startIdx) {
            buffer[idx--] = '1';
        }
    }

    private void subtractDigit(int[] f, int d) {
        if (d == 2) f[0] = Math.max(0, f[0] - 1);
        else if (d == 3) f[1] = Math.max(0, f[1] - 1);
        else if (d == 4) f[0] = Math.max(0, f[0] - 2);
        else if (d == 5) f[2] = Math.max(0, f[2] - 1);
        else if (d == 6) { f[0] = Math.max(0, f[0] - 1); f[1] = Math.max(0, f[1] - 1); }
        else if (d == 7) f[3] = Math.max(0, f[3] - 1);
        else if (d == 8) f[0] = Math.max(0, f[0] - 3);
        else if (d == 9) f[1] = Math.max(0, f[1] - 2);
    }

    public String smallestNumber(String num, long t) {
        int[] req = new int[4];
        for (int i = 0; i < 4; i++) {
            while (t % PRIMES[i] == 0) {
                req[i]++;
                t /= PRIMES[i];
            }
        }
        if (t > 1) return "-1";

        int n = num.length();
        char[] s = num.toCharArray();

        // Step 1: Forward scan to process the original prefix configuration
        int[][] prefixStates = new int[n + 1][4];
        prefixStates[0] = req.clone();
        
        int validLen = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] == '0') break;
            prefixStates[i + 1] = prefixStates[i].clone();
            subtractDigit(prefixStates[i + 1], s[i] - '0');
            validLen++;
        }

        // Case 1: Is the original string already structurally valid?
        if (validLen == n && prefixStates[n][0] == 0 && prefixStates[n][1] == 0 && prefixStates[n][2] == 0 && prefixStates[n][3] == 0) {
            return num;
        }

        // Step 2: Step backward from the rightmost break point to alter a single digit
        for (int pos = Math.min(n - 1, validLen); pos >= 0; pos--) {
            int[] baseReq = prefixStates[pos];
            int currentDigit = s[pos] - '0';
            
            for (int d = currentDigit + 1; d <= 9; d++) {
                int f2 = baseReq[0], f3 = baseReq[1], f5 = baseReq[2], f7 = baseReq[3];
                
                // Subtract current digit's factors inline
                if (d == 2 || d == 6 || d == 4 || d == 8) f2 = Math.max(0, f2 - (d == 2 ? 1 : d == 6 ? 1 : d == 4 ? 2 : 3));
                if (d == 3 || d == 6 || d == 9) f3 = Math.max(0, f3 - (d == 3 ? 1 : d == 6 ? 1 : 2));
                if (d == 5) f5 = Math.max(0, f5 - 1);
                if (d == 7) f7 = Math.max(0, f7 - 1);

                int remLen = n - 1 - pos;
                if (getMinDigitsNeeded(f2, f3, f5, f7) <= remLen) {
                    char[] ans = new char[n];
                    System.arraycopy(s, 0, ans, 0, pos);
                    ans[pos] = (char) ('0' + d);
                    fillSmallestSuffix(ans, pos + 1, n - 1, f2, f3, f5, f7);
                    return new String(ans);
                }
            }
        }

        // Case 3: Same length is impossible. Generate the strictly smallest number possible.
        int minDigitsNeeded = getMinDigitsNeeded(req[0], req[1], req[2], req[3]);
        int targetLen = Math.max(n + 1, minDigitsNeeded);
        
        char[] ans = new char[targetLen];
        fillSmallestSuffix(ans, 0, targetLen - 1, req[0], req[1], req[2], req[3]);
        return new String(ans);
    }
}
