import java.util.Arrays;

class Solution {
    private final int[] PRIMES = {2, 3, 5, 7};

    // Helper to calculate minimum digits needed to satisfy remaining prime factors
    private int getMinDigitsNeeded(int[] factors) {
        int f2 = factors[0];
        int f3 = factors[1];
        int f5 = factors[2];
        int f7 = factors[3];

        // Pack into 9s (3 * 3)
        int cnt9 = f3 / 2;
        f3 %= 2;

        // Pack into 8s (2 * 2 * 2)
        int cnt8 = f2 / 3;
        f2 %= 3;

        // Pack 7s and 5s directly
        int cnt7 = f7;
        int cnt5 = f5;

        // Pack remaining 6 (one 2 and one 3)
        int cnt6 = 0;
        if (f2 > 0 && f3 > 0) {
            cnt6 = 1;
            f2--;
            f3--;
        }

        // Pack remaining 4s (two 2s)
        int cnt4 = f2 / 2;
        f2 %= 2;

        // Pack remaining 3 and 2
        int cnt3 = f3;
        int cnt2 = f2;

        return cnt2 + cnt3 + cnt4 + cnt5 + cnt6 + cnt7 + cnt8 + cnt9;
    }

    // Generates the absolute smallest sorted suffix string for remaining factors
    private String makeSmallestSuffix(int[] factors, int length) {
        int f2 = factors[0];
        int f3 = factors[1];
        int f5 = factors[2];
        int f7 = factors[3];

        StringBuilder sb = new StringBuilder();

        // Greedily collect structural digits from 9 down to 2
        int cnt9 = f3 / 2; f3 %= 2; for (int i = 0; i < cnt9; i++) sb.append('9');
        int cnt8 = f2 / 3; f2 %= 3; for (int i = 0; i < cnt8; i++) sb.append('8');
        for (int i = 0; i < f7; i++) sb.append('7');
        for (int i = 0; i < f5; i++) sb.append('5');

        if (f2 > 0 && f3 > 0) {
            sb.append('6');
            f2--;
            f3--;
        }

        int cnt4 = f2 / 2; f2 %= 2; for (int i = 0; i < cnt4; i++) sb.append('4');
        if (f3 > 0) sb.append('3');
        if (f2 > 0) sb.append('2');

        // Reverse to sorted ascending order (e.g., "9987" becomes "7899")
        char[] digits = sb.toString().toCharArray();
        Arrays.sort(digits);

        // Pad front structural positions with '1's to match the required total length
        int onesNeeded = length - digits.length;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < onesNeeded; i++) {
            result.append('1');
        }
        result.append(digits);

        return result.toString();
    }

    private void removeDigitFactors(int[] factors, int d) {
        if (d <= 1) return;
        for (int i = 0; i < 4; i++) {
            while (d % PRIMES[i] == 0) {
                factors[i] = Math.max(0, factors[i] - 1);
                d /= PRIMES[i];
            }
        }
    }

    public String smallestNumber(String num, long t) {
        int[] req = new int[4];
        
        // Step 1: Prime factorize t
        for (int i = 0; i < 4; i++) {
            while (t % PRIMES[i] == 0) {
                req[i]++;
                t /= PRIMES[i];
            }
        }

        // If t contains any prime factors other than 2, 3, 5, or 7
        if (t > 1) {
            return "-1";
        }

        int n = num.length();

        // --- Case 1: Check if the original number is already valid ---
        int[] origFactors = req.clone();
        boolean isZeroFree = true;
        for (int i = 0; i < n; i++) {
            char c = num.charAt(i);
            if (c == '0') {
                isZeroFree = false;
                break;
            }
            removeDigitFactors(origFactors, c - '0');
        }

        boolean origSatisfied = true;
        for (int x : origFactors) {
            if (x > 0) origSatisfied = false;
        }
        if (isZeroFree && origSatisfied) {
            return num;
        }

        // --- Case 2: Prefilter historical states to optimize backward search ---
        int[][] prefixHistory = new int[n + 1][4];
        prefixHistory[0] = req.clone();
        int validPrefixLen = 0;

        for (int i = 0; i < n; i++) {
            char c = num.charAt(i);
            if (c == '0') break; // No valid configurations can continue past a 0
            
            prefixHistory[i + 1] = prefixHistory[i].clone();
            removeDigitFactors(prefixHistory[i + 1], c - '0');
            validPrefixLen++;
        }

        // --- Case 3: Scan from right to left to change a single digit ---
        for (int pos = n - 1; pos >= 0; pos--) {
            if (pos > validPrefixLen) continue;

            int[] currentReq = prefixHistory[pos];
            int startDigit = num.charAt(pos) - '0' + 1;

            for (int d = startDigit; d <= 9; d++) {
                int[] testReq = currentReq.clone();
                removeDigitFactors(testReq, d);

                int remLen = n - 1 - pos;
                if (getMinDigitsNeeded(testReq) <= remLen) {
                    String prefix = num.substring(0, pos) + d;
                    String suffix = makeSmallestSuffix(testReq, remLen);
                    return prefix + suffix;
                }
            }
        }

        // --- Case 4: Increase length if same structural length is impossible ---
        return makeSmallestSuffix(req, n + 1);
    }
}
