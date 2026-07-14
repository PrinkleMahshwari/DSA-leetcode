import java.util.*;

class Solution {
    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {
        int maxVal = 0;
        for (int num : nums) {
            if (num > maxVal) {
                maxVal = num;
            }
        }

        // 1. Calculate precise element frequency counts
        int[] freq = new int[maxVal + 1];
        for (int num : nums) {
            freq[num]++;
        }

        // Precompute total counts of multiples for each value up to maxVal
        int[] mul = new int[maxVal + 1];
        for (int v = 1; v <= maxVal; v++) {
            for (int x = v; x <= maxVal; x += v) {
                mul[v] += freq[x];
            }
        }

        // 2. Precompute powers of 2 and 3 to eliminate math loop overheads
        int n = nums.length;
        long[] pow2 = new long[n + 1];
        long[] pow3 = new long[n + 1];
        pow2[0] = 1;
        pow3[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow2[i] = (pow2[i - 1] * 2) % MOD;
            pow3[i] = (pow3[i - 1] * 3) % MOD;
        }

        // 3. Dynamic 2D Exclusion table scaled tightly to maxVal's exact limit
        long[][] valid = new long[maxVal + 1][maxVal + 1];
        long totalAnswer = 0;

        // Iterate backwards over all pairs of possible divisors (f, s)
        for (int f = maxVal; f >= 1; f--) {
            for (int s = maxVal; s >= 1; s--) {
                int g = gcd(f, s);
                // Avoid potential overflow during multiplication for large bounds
                long lcmVal = ((long) f * s) / g;
                
                int c_both = (lcmVal <= maxVal) ? mul[(int) lcmVal] : 0;
                int c_f = mul[f];
                int c_s = mul[s];

                int c_f_only = c_f - c_both;
                int c_s_only = c_s - c_both;

                // Combinations allowing elements to match the division structures
                long totalWays = (pow3[c_both] * pow2[c_f_only] % MOD) * pow2[c_s_only] % MOD;

                // Subtract configurations producing empty sets
                totalWays = (totalWays - pow2[c_s] - pow2[c_f] + 1) % MOD;
                if (totalWays < 0) {
                    totalWays += MOD;
                }

                // Sieve out strict multiples that have already been computed 
                long strictMultiplesSubtract = 0;
                int maxI = maxVal / f;
                int maxJ = maxVal / s;
                
                for (int i = 1; i <= maxI; i++) {
                    int nextF = f * i;
                    for (int j = 1; j <= maxJ; j++) {
                        if (i == 1 && j == 1) continue;
                        strictMultiplesSubtract = (strictMultiplesSubtract + valid[nextF][s * j]) % MOD;
                    }
                }

                long exactGcdPairs = (totalWays - strictMultiplesSubtract) % MOD;
                if (exactGcdPairs < 0) {
                    exactGcdPairs += MOD;
                }

                valid[f][s] = exactGcdPairs;

                // Accumulate valid pairs where both subsequences share the exact same GCD
                if (f == s) {
                    totalAnswer = (totalAnswer + exactGcdPairs) % MOD;
                }
            }
        }

        return (int) totalAnswer;
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
