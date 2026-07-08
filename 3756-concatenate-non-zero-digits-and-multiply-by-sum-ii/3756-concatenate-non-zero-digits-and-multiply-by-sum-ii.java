import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;
    public int[] sumAndMultiply(String s, int[][] queries) {
        
        int n = s.length();
        int q = queries.length;

        // step 1: compress the string by keeping only non-zero digits 
        // store both digits value and its original position
        ArrayList<Integer> digitList = new ArrayList<>();
        ArrayList<Integer> positionList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int digit = s.charAt(i) - '0';

            if (digit != 0) {
                digitList.add(digit);
                positionList.add(i);
            }
        }

        int compressedLength = digitList.size();

        // if the string contains no non-zero digits, every query immediately evaluates to 0
        if (compressedLength == 0)
            return new int[q];
        
        int[] digits = new int[compressedLength];
        int[] positions = new int[compressedLength];

        for (int i = 0; i < compressedLength; i++) {
            digits[i] = digitList.get(i);
            positions[i] = positionList.get(i);
        }

        // step 2: prefix sum of digit valus allows O(1)  digit-sum queries
        long[] digitPrefix = new long[compressedLength + 1];

        for (int i = 0; i < compressedLength; i++)
            digitPrefix[i + 1] = digitPrefix[i] + digits[i];
        
        // step 3: precompute powers of 10 modulo MOD
        long[] power10 = new long[compressedLength + 1];
        power10[0] = 1;

        for (int i = 1; i <= compressedLength; i++)
            power10[i] = (power10[i - 1] * 10) % MOD;
        
        // step 4: prefix concatenation vlaues
        // prefixNumber[i] represents the concatenated value pf digits[0..i-1] modulo MOD
        long[] prefixNumber = new long[compressedLength + 1];

        for (int i = 0; i < compressedLength; i++) 
            prefixNumber[i + 1] = (prefixNumber[i] * 10 + digits[i]) % MOD;
        
        int[] answer = new int[q];

        // step 5: process every query independently
        for (int i = 0; i < q; i++) {

            int left = queries[i][0];
            int right = queries[i][1];

            // locate the first non-zero digit inside [left, right]
            int compressedLeft = lowerBound(positions, left);

            // locate the last non-zero digit inside [left, right]
            int compressedRight = upperBound(positions, right) - 1;

            // no non-zero digits exist inside this substring
            if (compressedLeft > compressedRight) {
                answer[i] = 0;
                continue;
            }

            // retrieve digit sum in O(1)
            long digitSum = digitPrefix[compressedRight + 1] - digitPrefix[compressedLeft];

            // number of compressed digits inside this substring
            int length = compressedRight - compressedLeft + 1;

            // retrieve concatenated value in O(1)
            long concatenatedNumber = (prefixNumber[compressedRight + 1] - (prefixNumber[compressedLeft] * power10[length]) % MOD + MOD) % MOD;

            // final answer
            answer[i] = (int) ((concatenatedNumber * digitSum) % MOD);
        }

        return answer;
    }

    // first index whose position >=mtarget
    private int lowerBound(int[] positions, int target) {

        int left = 0;
        int right = positions.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (positions[mid] < target)
                left = mid + 1;
            else
                right = mid;
        }
        
        return left;
    }

    // first index whose position > target
    private int upperBound(int[] positions, int target) {

        int left = 0;
        int right = positions.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (positions[mid] <= target)
                left = mid + 1;
            else
                right = mid;
        }
        
        return left;
    }
}