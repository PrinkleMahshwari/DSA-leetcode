import java.util.*;

class Solution {

    public int[] arrayRankTransform(int[] arr) {
        int n = arr.length;

        if (n == 0) return arr;

        // pack (value, original index) into a single primitive long per element:
        // value shifted into high bits, index in low bits. this lets us sort a
        // plain long[] with Arrays.sort's dual-pivot quicksort (primitive, no
        // boxing, no comparator call overhead) instead of Integer[] + comparator,
        // which was the actual runtime cost in the previous version
        long[] packed = new long[n];
        int indexBits = 20; // 2^20 > 10^5, enough room for any valid index
        long indexMask = (1L << indexBits) - 1;

        for (int i = 0; i < n; i++) {
            // shift value up so it sorts correctly (add offset to keep it non-negative
            // pre-shift, avoids sign bit interfering with ordering)
            long shiftedValue = (long) arr[i] + 2_000_000_000L;
            packed[i] = (shiftedValue << indexBits) | i;
        }

        Arrays.sort(packed); // primitive long[] sort, no boxing anywhere

        int[] result = new int[n];
        int rank = 1;

        int prevIndex = (int) (packed[0] & indexMask);
        result[prevIndex] = rank;

        for (int i = 1; i < n; i++) {
            long prevShiftedValue = packed[i - 1] >> indexBits;
            long curShiftedValue = packed[i] >> indexBits;

            if (curShiftedValue != prevShiftedValue) {
                rank++;
            }

            int curIndex = (int) (packed[i] & indexMask);
            result[curIndex] = rank;
        }

        return result;
    }
}