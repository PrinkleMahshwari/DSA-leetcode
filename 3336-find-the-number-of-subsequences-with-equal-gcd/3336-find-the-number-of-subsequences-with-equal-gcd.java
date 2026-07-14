import java.util.*;

class Solution {

    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {
        int n = nums.length;

        int maxVal = 1;
        for (int num : nums) maxVal = Math.max(maxVal, num);
        int size = maxVal + 1;

        // freq of each value, single pass, no gcd computation per element
        int[] cnt = new int[size];
        for (int num : nums) cnt[num]++;

        // c[d] = count of elements divisible by d, standard divisor sieve
        // this replaces the per-element gcd-update DP entirely -- n only
        // shows up HERE, everything after this is pure O(maxVal^2 log maxVal)
        int[] c = new int[size];
        for (int d = 1; d <= maxVal; d++) {
            for (int v = d; v <= maxVal; v += d) {
                c[d] += cnt[v];
            }
        }

        // powers of 2 and 3 up to n, since c[d] can never exceed n
        long[] pow2 = new long[n + 1];
        long[] pow3 = new long[n + 1];
        pow2[0] = 1; pow3[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow2[i] = (pow2[i - 1] * 2) % MOD;
            pow3[i] = (pow3[i - 1] * 3) % MOD;
        }

        // N[a][b] = # disjoint nonempty pairs (A,B) with gcd(A) a multiple of a,
        // gcd(B) a multiple of b -- pure combinatorics on divisor counts,
        // each element gets 3/2/2/1 choices depending on which of a,b it's
        // divisible by, corrected for A-empty / B-empty via inclusion-exclusion
        long[] N = new long[size * size];

        for (int a = 1; a <= maxVal; a++) {
            int aRow = a * size;
            for (int b = 1; b <= maxVal; b++) {
                int g = gcd(a, b);
                long lcm = (long) (a / g) * b;

                int both = (lcm <= maxVal) ? c[(int) lcm] : 0;
                int onlyA = c[a] - both;
                int onlyB = c[b] - both;

                long raw = pow3[both] * pow2[onlyA] % MOD * pow2[onlyB] % MOD;
                long val = (raw - pow2[c[b]] - pow2[c[a]] + 1 + 2L * MOD) % MOD;

                N[aRow + b] = val;
            }
        }

        // 2D Mobius inversion done as two independent 1D subtract-multiples
        // passes (one per axis) -- turns "multiple of a/b" counts into
        // "exactly a/b" counts. this factorization works because the original
        // zeta transform is separable across the two axes
        for (int b = 1; b <= maxVal; b++) {
            for (int a = maxVal; a >= 1; a--) {
                long sum = 0;
                for (int a2 = 2 * a; a2 <= maxVal; a2 += a) {
                    sum += N[a2 * size + b];
                }
                N[a * size + b] = ((N[a * size + b] - sum) % MOD + MOD) % MOD;
            }
        }

        for (int a = 1; a <= maxVal; a++) {
            int aRow = a * size;
            for (int b = maxVal; b >= 1; b--) {
                long sum = 0;
                for (int b2 = 2 * b; b2 <= maxVal; b2 += b) {
                    sum += N[aRow + b2];
                }
                N[aRow + b] = ((N[aRow + b] - sum) % MOD + MOD) % MOD;
            }
        }

        // N[g][g] is now exact(g,g): pairs with gcd(A)=gcd(B)=g exactly
        long answer = 0;
        for (int g = 1; g <= maxVal; g++) {
            answer = (answer + N[g * size + g]) % MOD;
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