import java.util.*;

class Solution {

    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {

        int n = nums.length;

        int maxVal = 1;
        for (int num : nums) maxVal = Math.max(maxVal, num);

        int size = maxVal + 1;

        int[] gcdTable = new int[size * size];
        for (int a = 0; a <= maxVal; a++) {
            for (int b = 0; b <= maxVal; b++) {
                gcdTable[a * size + b] = gcd(a, b);
            }
        }

        long[] dp = new long[size * size];
        long[] next = new long[size * size];
        dp[0] = 1;

        // tracks the highest gcd value reachable so far, since dp is 0
        // everywhere beyond it -- lets us shrink the g1/g2 loop bounds as we
        // go instead of always scanning up to the global maxVal
        int reachableMax = 0;

        for (int num : nums) {
            Arrays.fill(next, 0L);

            int numRow = num * size;
            int newReachableMax = Math.max(reachableMax, num);

            for (int g1 = 0; g1 <= reachableMax; g1++) {
                int g1Base = g1 * size;
                int gcdG1Val = (g1 == 0) ? num : gcdTable[g1Base + num];

                for (int g2 = 0; g2 <= reachableMax; g2++) {
                    long ways = dp[g1Base + g2];
                    if (ways == 0) continue;

                    int idx = g1Base + g2;

                    // deferred modulo: raw sums stay well within long range for
                    // an entire element's sweep (bounded well under long max),
                    // so accumulate freely here and reduce mod calls to a single
                    // pass at the end, instead of one mod per addition (3x fewer
                    // mod operations overall, which is a real cost at this scale)
                    next[idx] += ways;

                    int newG1 = gcdG1Val;
                    int idx2 = newG1 * size + g2;
                    next[idx2] += ways;

                    int newG2 = (g2 == 0) ? num : gcdTable[g2 * size + num];
                    int idx3 = g1Base + newG2;
                    next[idx3] += ways;

                    if (newG1 > newReachableMax) newReachableMax = newG1;
                    if (newG2 > newReachableMax) newReachableMax = newG2;
                }
            }

            // single mod pass over only the range that could possibly be nonzero,
            // instead of mod-ing on every individual addition above
            int boundedSize = (newReachableMax + 1) * size;
            for (int idx = 0; idx < boundedSize; idx++) {
                if (next[idx] >= MOD) next[idx] %= MOD;
            }

            reachableMax = newReachableMax;

            long[] tmp = dp;
            dp = next;
            next = tmp;
        }

        long answer = 0;
        for (int g = 1; g <= maxVal; g++) {
            answer = (answer + dp[g * size + g]) % MOD;
        }

        return (int) answer;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}