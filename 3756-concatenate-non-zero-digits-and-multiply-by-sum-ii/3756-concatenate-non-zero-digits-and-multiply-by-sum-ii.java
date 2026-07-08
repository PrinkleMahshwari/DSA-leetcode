import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;

    public int[] sumAndMultiply(String s, int[][] queries) {

        int n = s.length();
        int q = queries.length;
        char[] chars = s.toCharArray();

        // count non-zero digits first; this avoids ever allocating separate
        // digits[]/positions[] scratch arrays like the earlier version did --
        // everything below is built directly off chars[]
        int compressedLength = 0;
        for (int i = 0; i < n; i++) {
            if (chars[i] != '0') compressedLength++;
        }

        if (compressedLength == 0) {
            return new int[q];
        }

        // build digitPrefix, power10, prefixNumber in a single forward pass,
        // reading straight from chars[] instead of from an intermediate digits[] array
        long[] digitPrefix = new long[compressedLength + 1];
        long[] power10 = new long[compressedLength + 1];
        long[] prefixNumber = new long[compressedLength + 1];
        power10[0] = 1;

        int c = 0;
        for (int i = 0; i < n; i++) {
            int digit = chars[i] - '0';
            if (digit == 0) continue;

            digitPrefix[c + 1] = digitPrefix[c] + digit;
            power10[c + 1] = (power10[c] * 10) % MOD;
            prefixNumber[c + 1] = (prefixNumber[c] * 10 + digit) % MOD;
            c++;
        }

        // nextNonZero[i] = compressed index of the first non-zero digit at
        // position >= i (or compressedLength if none exists beyond i).
        // single backward pass tracking the compressed index directly,
        // replaces both the old posToCompressed[] array and any binary search
        int[] nextNonZero = new int[n + 1];
        int backwardIdx = compressedLength;
        nextNonZero[n] = compressedLength;
        for (int i = n - 1; i >= 0; i--) {
            if (chars[i] != '0') backwardIdx--;
            nextNonZero[i] = backwardIdx;
        }

        // prevNonZero[i] = compressed index of the last non-zero digit at
        // position <= i (or -1 if none exists up to i). single forward pass
        int[] prevNonZero = new int[n];
        int forwardIdx = -1;
        for (int i = 0; i < n; i++) {
            if (chars[i] != '0') forwardIdx++;
            prevNonZero[i] = forwardIdx;
        }

        int[] answer = new int[q];

        // every query resolves in O(1): two array lookups, no search of any kind
        for (int i = 0; i < q; i++) {
            int left = queries[i][0];
            int right = queries[i][1];

            int compressedLeft = nextNonZero[left];
            int compressedRight = prevNonZero[right];

            if (compressedLeft >= compressedLength || compressedRight < 0 || compressedLeft > compressedRight) {
                answer[i] = 0;
                continue;
            }

            long digitSum = digitPrefix[compressedRight + 1] - digitPrefix[compressedLeft];
            int length = compressedRight - compressedLeft + 1;

            long concatenatedNumber = (prefixNumber[compressedRight + 1]
                    - (prefixNumber[compressedLeft] * power10[length]) % MOD + MOD) % MOD;

            answer[i] = (int) ((concatenatedNumber * digitSum) % MOD);
        }

        return answer;
    }
}