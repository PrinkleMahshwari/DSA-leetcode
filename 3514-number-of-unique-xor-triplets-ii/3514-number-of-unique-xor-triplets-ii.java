import java.util.*;

class Solution {
    public int uniqueXorTriplets(int[] nums) {

        int maxVal = 0;
        for (int num : nums) maxVal = Math.max(maxVal, num);

        // smallest power-of-two strictly greater than maxVal safely bounds
        // any XOR combination of values up to maxVal
        int bound = 1;
        while (bound <= maxVal) bound <<= 1;

        boolean[] present = new boolean[bound];
        for (int num : nums) present[num] = true;

        // collect distinct present values once -- this is the key fix,
        // separates "which values exist" from "how many total occurrences",
        // since occurrence count never matters (any single occurrence can
        // be reused across i, j, k thanks to i<=j<=k allowing repeats)
        int[] distinct = new int[bound];
        int distinctCount = 0;
        for (int v = 0; v < bound; v++) {
            if (present[v]) distinct[distinctCount++] = v;
        }

        // step 1: all achievable XORs of any TWO present values (a^b, a==b
        // allowed since i can equal j). O(distinctCount^2) -- crucially,
        // NO multiplication by 2048 here, that's what caused the TLE before
        boolean[] pairXor = new boolean[bound];
        for (int a = 0; a < distinctCount; a++) {
            int va = distinct[a];
            for (int b = a; b < distinctCount; b++) {
                pairXor[va ^ distinct[b]] = true;
            }
        }

        // step 2: combine every achievable pair-XOR with every present
        // single value to get triplet XORs. O(bound * distinctCount),
        // still bounded and independent of any nested 2048x2048 blowup
        boolean[] tripletXor = new boolean[bound];
        for (int p = 0; p < bound; p++) {
            if (!pairXor[p]) continue;
            for (int c = 0; c < distinctCount; c++) {
                tripletXor[p ^ distinct[c]] = true;
            }
        }

        int count = 0;
        for (int i = 0; i < bound; i++) {
            if (tripletXor[i]) count++;
        }

        return count;
    }
}