import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;

    public int[] sumAndMultiply(String s, int[][] queries) {

        int n = s.length();
        int q = queries.length;

        // convert once to char array, avoids repeated charAt() into the String object
        char[] chars = s.toCharArray();

        // posToCompressed[i] = compressed index of the digit at position i if it's
        // non-zero, otherwise -1. built directly with primitive arrays, no
        // ArrayList<Integer> boxing/unboxing overhead for up to n elements
        int[] posToCompressed = new int[n];
        int compressedLength = 0;

        for (int i = 0; i < n; i++) {
            int digit = chars[i] - '0';
            if (digit != 0) {
                posToCompressed[i] = compressedLength;
                compressedLength++;
            } else {
                posToCompressed[i] = -1;
            }
        }

        // no non-zero digits anywhere, every query is trivially 0
        if (compressedLength == 0) {
            return new int[q];
        }

        int[] digits = new int[compressedLength];
        int[] positions = new int[compressedLength];

        // second pass fills the compressed digit/position arrays directly by index,
        // avoids the earlier List.get(i) boxing conversion loop entirely
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if (posToCompressed[i] != -1) {
                digits[idx] = chars[i] - '0';
                positions[idx] = i;
                idx++;
            }
        }

        // prefix sum of digit values for O(1) digit-sum queries
        long[] digitPrefix = new long[compressedLength + 1];
        for (int i = 0; i < compressedLength; i++) {
            digitPrefix[i + 1] = digitPrefix[i] + digits[i];
        }

        // precompute powers of 10 modulo MOD
        long[] power10 = new long[compressedLength + 1];
        power10[0] = 1;
        for (int i = 1; i <= compressedLength; i++) {
            power10[i] = (power10[i - 1] * 10) % MOD;
        }

        // prefix concatenation values: prefixNumber[i] is digits[0..i-1] concatenated mod MOD
        long[] prefixNumber = new long[compressedLength + 1];
        for (int i = 0; i < compressedLength; i++) {
            prefixNumber[i + 1] = (prefixNumber[i] * 10 + digits[i]) % MOD;
        }

        // nextNonZero[i] = compressed index of the first non-zero digit at
        // position >= i, or compressedLength (sentinel, out of range) if none exists.
        // built with one backward scan instead of a binary search per query,
        // turning compressedLeft lookup into O(1) instead of O(log n)
        int[] nextNonZero = new int[n + 1];
        nextNonZero[n] = compressedLength;
        for (int i = n - 1; i >= 0; i--) {
            nextNonZero[i] = (posToCompressed[i] != -1) ? posToCompressed[i] : nextNonZero[i + 1];
        }

        // prevNonZero[i] = compressed index of the last non-zero digit at
        // position <= i, or -1 (sentinel, out of range) if none exists.
        // built with one forward scan, replaces the upperBound binary search per query
        int[] prevNonZero = new int[n];
        int running = -1;
        for (int i = 0; i < n; i++) {
            if (posToCompressed[i] != -1) {
                running = posToCompressed[i];
            }
            prevNonZero[i] = running;
        }

        int[] answer = new int[q];

        // every query now resolves in true O(1), no binary search calls at all
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