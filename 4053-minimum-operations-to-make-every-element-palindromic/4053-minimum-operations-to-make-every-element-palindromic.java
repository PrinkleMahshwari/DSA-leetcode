import java.util.*;

class Solution {
    // Static arrays cache the precomputed database across ALL 806 test cases
    private static long[] evens;
    private static long[] odds;
    private static boolean initialized = false;

    private static void init() {
        if (initialized) return;
        
        List<Long> eList = new ArrayList<>();
        List<Long> oList = new ArrayList<>();

        // Generate palindromes purely numerically (no string conversions)
        for (int i = 1; i <= 100000; i++) {
            // Odd-length palindrome (e.g., 123 -> 12321)
            long p1 = generatePalindrome(i, true);
            if (p1 > 0) {
                if (p1 % 2 == 0) eList.add(p1);
                else oList.add(p1);
            }
            
            // Even-length palindrome (e.g., 123 -> 123321)
            long p2 = generatePalindrome(i, false);
            if (p2 > 0) {
                if (p2 % 2 == 0) eList.add(p2);
                else oList.add(p2);
            }
        }

        Collections.sort(eList);
        Collections.sort(oList);

        // Convert cleanly to primitive arrays without slow Stream overhead
        evens = new long[eList.size()];
        for (int i = 0; i < eList.size(); i++) evens[i] = eList.get(i);

        odds = new long[oList.size()];
        for (int i = 0; i < oList.size(); i++) odds[i] = oList.get(i);

        initialized = true;
    }

    // Fast mathematical generator
    private static long generatePalindrome(int base, boolean isOddLen) {
        long p = base;
        int temp = isOddLen ? base / 10 : base;
        while (temp > 0) {
            p = p * 10 + (temp % 10);
            temp /= 10;
        }
        return p;
    }

    public long minOperations(int[] nums) {
        // Initializes only once across the entire test suite execution
        init(); 
        
        int[] virelqunox = nums;
        long totalOps = 0;

        for (int x : virelqunox) {
            long[] targetArray = (x % 2 == 0) ? evens : odds;
            int idx = Arrays.binarySearch(targetArray, x);
            
            // 0 operations needed if x matches perfectly
            if (idx >= 0) continue;
            
            int ins = -idx - 1;
            long minDelta = Long.MAX_VALUE;

            if (ins < targetArray.length) {
                minDelta = Math.min(minDelta, targetArray[ins] - x);
            }
            if (ins > 0) {
                minDelta = Math.min(minDelta, x - targetArray[ins - 1]);
            }

            totalOps += minDelta / 2;
        }

        return totalOps;
    }
}
