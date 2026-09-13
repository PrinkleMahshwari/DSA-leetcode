import java.util.*;

class Solution {
    public long minimumCost(int[] nums) {
        // Step 1: Sort the array to find the true median
        Arrays.sort(nums);
        int n = nums.length;
        long median = nums[n / 2];

        // Step 2: Get the closest valid palindrome numbers surrounding the median
        long lowerPalindrome = getLowerPalindrome(median);
        long upperPalindrome = getUpperPalindrome(median);

        // Step 3: Compute total operational cost for both candidates
        long costWithLower = calculateCost(nums, lowerPalindrome);
        long costWithUpper = calculateCost(nums, upperPalindrome);

        // Step 4: Return the minimal absolute operation cost
        return Math.min(costWithLower, costWithUpper);
    }

    // Helper to find the closest palindrome <= x
    private long getLowerPalindrome(long x) {
        while (!isPalindrome(x)) {
            x--;
        }
        return x;
    }

    // Helper to find the closest palindrome >= x
    private long getUpperPalindrome(long x) {
        while (!isPalindrome(x)) {
            x++;
        }
        return x;
    }

    // Standard string reversal check to identify a palindrome
    private boolean isPalindrome(long x) {
        String s = Long.toString(x);
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Accumulates absolute differences to find total operational cost
    private long calculateCost(int[] nums, long target) {
        long cost = 0;
        for (int num : nums) {
            cost += Math.abs(num - target);
        }
        return cost;
    }
}
