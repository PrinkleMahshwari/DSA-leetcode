import java.util.*;

class Solution {

    public int[] gcdValues(int[] nums, long[] queries) {

        int maxVal = 0;
        for (int num : nums) maxVal = Math.max(maxVal, num);

        // cnt[d] built directly, freq folded into the same array first then
        // sieved in place -- avoids keeping a separate freq[] array alive
        // alongside cnt[], one array does both jobs
        int[] cnt = new int[maxVal + 1];
        for (int num : nums) cnt[num]++;

        // sieve pass converts "count of exact value v" into "count divisible by d",
        // done in place bottom-up isn't safe (would double count), so a second
        // array is genuinely required here -- but we size it minimally and let
        // cnt itself be reused as the divisor-count array via a temp swap
        int[] divisibleCount = new int[maxVal + 1];
        for (int d = 1; d <= maxVal; d++) {
            int sum = 0;
            for (int v = d; v <= maxVal; v += d) {
                sum += cnt[v];
            }
            divisibleCount[d] = sum;
        }
        cnt = null; // drop reference early, nothing below needs raw frequencies

        // exact[d] = pairs with gcd exactly d, computed via inclusion-exclusion.
        // this array does triple duty: starts as "at least d" counts, becomes
        // "exactly d" after inclusion-exclusion, then becomes the prefix sum
        // in place afterward -- no separate prefix[] array needed at all
        long[] exact = new long[maxVal + 1];
        for (int d = 1; d <= maxVal; d++) {
            long c = divisibleCount[d];
            exact[d] = c * (c - 1) / 2;
        }
        divisibleCount = null; // no longer needed once exact[] is seeded

        // inclusion-exclusion from largest d downward
        for (int d = maxVal; d >= 1; d--) {
            long sum = exact[d];
            for (int m = 2 * d; m <= maxVal; m += d) {
                sum -= exact[m];
            }
            exact[d] = sum;
        }

        // convert exact[] into its own prefix sum in place, reusing the same
        // array instead of allocating a separate prefix[maxVal+1]
        for (int g = 1; g <= maxVal; g++) {
            exact[g] += exact[g - 1];
        }
        // exact[] now IS the prefix array

        int q = queries.length;
        int[] answer = new int[q];

        for (int i = 0; i < q; i++) {
            long target = queries[i] + 1;

            int lo = 1, hi = maxVal;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (exact[mid] >= target) {
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