import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;

    public int[] sumAndMultiply(String s, int[][] queries) {

        int n = s.length();
        int q = queries.length;
        char[] chars = s.toCharArray();

        // single forward pass builds digitPrefix, power10, prefixNumber, AND
        // prevNonZero all at once, using compressedLength as a running counter
        // instead of a separate counting pass first. arrays are allocated at
        // size n+1 (worst case: every char non-zero) so we never need to know
        // the final compressedLength in advance -- this merges what was
        // previously two separate forward passes into one
        int[] digitPrefix = new int[n + 1]; // int is enough: max sum is 9*10^5, no overflow, no mod needed
        long[] power10 = new long[n + 1];
        long[] prefixNumber = new long[n + 1];
        int[] prevNonZero = new int[n];
        power10[0] = 1;

        int c = 0;
        for (int i = 0; i < n; i++) {
            int digit = chars[i] - '0';
            if (digit != 0) {
                digitPrefix[c + 1] = digitPrefix[c] + digit;
                power10[c + 1] = (power10[c] * 10) % MOD;
                prefixNumber[c + 1] = (prefixNumber[c] * 10 + digit) % MOD;
                c++;
            }
            // prevNonZero tracks the compressed index as of THIS position,
            // filled in the same forward pass instead of a dedicated separate loop
            prevNonZero[i] = c - 1;
        }

        int compressedLength = c;

        if (compressedLength == 0) {
            return new int[q];
        }

        // nextNonZero[i] = compressed index of first non-zero digit at position >= i.
        // needs a backward scan since it depends on future positions, so this
        // stays a separate pass -- can't be merged with the forward one above
        int[] nextNonZero = new int[n + 1];
        int backwardIdx = compressedLength;
        nextNonZero[n] = compressedLength;
        for (int i = n - 1; i >= 0; i--) {
            if (chars[i] != '0') backwardIdx--;
            nextNonZero[i] = backwardIdx;
        }

        int[] answer = new int[q];

        for (int i = 0; i < q; i++) {
            int[] query = queries[i];
            int left = query[0];
            int right = query[1];

            int compressedLeft = nextNonZero[left];
            int compressedRight = prevNonZero[right];

            if (compressedLeft >= compressedLength || compressedRight < 0 || compressedLeft > compressedRight) {
                continue; // answer[i] already 0 by default, skip the write
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