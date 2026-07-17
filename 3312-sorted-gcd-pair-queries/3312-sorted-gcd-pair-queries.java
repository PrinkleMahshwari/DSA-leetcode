import java.util.*;

class Solution {

    public int[] gcdValues(int[] nums, long[] queries) {

        int maxVal = 0;
        for (int num : nums) maxVal = Math.max(maxVal, num);

        // freq[v] = how many elements equal v
        int[] freq = new int[maxVal + 1];
        for (int num : nums) freq[num]++;

        // cnt[d] = how many elements are divisible by d, via divisor sieve
        int[] cnt = new int[maxVal + 1];
        for (int d = 1; d <= maxVal; d++) {
            for (int v = d; v <= maxVal; v += d) {
                cnt[d] += freq[v];
            }
        }

        // atLeast[d] = number of pairs where BOTH elements divisible by d
        // (this overcounts pairs whose actual gcd is a multiple of d, not just d)
        long[] exact = new long[maxVal + 1];
        for (int d = 1; d <= maxVal; d++) {
            long c = cnt[d];
            exact[d] = c * (c - 1) / 2;
        }

        // inclusion-exclusion from largest d downward: subtract exact counts
        // of all proper multiples of d, leaving exact[d] = pairs whose gcd is EXACTLY d
        for (int d = maxVal; d >= 1; d--) {
            for (int m = 2 * d; m <= maxVal; m += d) {
                exact[d] -= exact[m];
            }
        }

        // prefix sum of exact[] gives, for each gcd value g, how many total
        // pairs have gcd <= g -- this directly maps a sorted-array index to
        // a gcd value via binary search, no need to materialize gcdPairs itself
        long[] prefix = new long[maxVal + 1];
        for (int g = 1; g <= maxVal; g++) {
            prefix[g] = prefix[g - 1] + exact[g];
        }

        int q = queries.length;
        int[] answer = new int[q]; // gcd values always fit in int (max 5*10^4)

        for (int i = 0; i < q; i++) {
            long target = queries[i] + 1; // queries[i] is a long, index can exceed int range

            // binary search smallest g such that prefix[g] >= target
            int lo = 1, hi = maxVal;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (prefix[mid] >= target) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }

            answer[i] = lo;
        }

        return answer;
    }
}