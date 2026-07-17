import java.util.*;

class Solution {

    public int[] gcdValues(int[] nums, long[] queries) {

        int maxVal = 0;
        for (int num : nums) maxVal = Math.max(maxVal, num);

        // cnt[v] starts as raw frequency of value v
        int[] cnt = new int[maxVal + 1];
        for (int num : nums) cnt[num]++;

        // in-place divisor-sum sieve (zeta transform), ASCENDING d:
        // when computing cnt[d], every cnt[m] read (m = 2d, 3d, ...) still
        // holds its RAW frequency value, because writes only ever happen at
        // index d itself on this iteration -- larger indices haven't been
        // touched yet. This is what makes single-array in-place safe; doing
        // it descending would read already-aggregated values and overcount.
        for (int d = 1; d <= maxVal; d++) {
            int sum = cnt[d];
            for (int m = 2 * d; m <= maxVal; m += d) {
                sum += cnt[m];
            }
            cnt[d] = sum;
        }
        // cnt[d] now IS "count of elements divisible by d" -- no second
        // array was ever needed for this step

        // exact[] reused for three roles in place: "at least d" -> "exactly d"
        // (inclusion-exclusion) -> prefix sum. still needs its own long[]
        // since values can exceed int range (up to ~5*10^9 pairs total)
        long[] exact = new long[maxVal + 1];
        for (int d = 1; d <= maxVal; d++) {
            long c = cnt[d];
            exact[d] = c * (c - 1) / 2;
        }
        cnt = null; // drop reference, nothing below needs divisor counts anymore

        // inclusion-exclusion from largest d downward, local sum avoids
        // repeated array writes inside the inner loop
        for (int d = maxVal; d >= 1; d--) {
            long sum = exact[d];
            for (int m = 2 * d; m <= maxVal; m += d) {
                sum -= exact[m];
            }
            exact[d] = sum;
        }

        // convert exact[] into its own prefix sum in place
        for (int g = 1; g <= maxVal; g++) {
            exact[g] += exact[g - 1];
        }
        // exact[] now IS the prefix array too

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