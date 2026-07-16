import java.util.*;

class Solution {

    public long gcdSum(int[] nums) {
        int n = nums.length;

        // build prefixGcd in place using a running max, no extra tracking needed
        int[] prefixGcd = new int[n];
        int runningMax = 0;

        for (int i = 0; i < n; i++) {
            runningMax = Math.max(runningMax, nums[i]);
            prefixGcd[i] = gcd(nums[i], runningMax);
        }

        Arrays.sort(prefixGcd);

        long answer = 0;

        // two pointer from both ends, skip the unpaired middle automatically
        // when n is odd since left crosses right before ever meeting it
        int left = 0, right = n - 1;
        while (left < right) {
            answer += gcd(prefixGcd[left], prefixGcd[right]);
            left++;
            right--;
        }

        return answer;
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