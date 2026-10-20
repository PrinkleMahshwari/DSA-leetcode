using System;

public class Solution {
    public int SortableIntegers(int[] nums) {
        int n = nums.Length;

        int[] sorted = (int[])nums.Clone();
        Array.Sort(sorted);

        int answer = 0;

        // Only divisors of n can be sortable
        for (int k = 1; k <= n; k++) {
            if (n % k != 0) continue;

            bool sortable = true;

            // Check every block of length k
            for (int start = 0; start < n; start += k) {
                if (!IsRotation(nums, sorted, start, k)) {
                    sortable = false;
                    break;
                }
            }

            if (sortable) answer += k;
        }

        return answer;
    }

    private bool IsRotation(int[] nums, int[] sorted, int start, int k) {
        // Build the KMP prefix table for the sorted block
        int[] lps = new int[k];
        int j = 0;

        for (int i = 1; i < k; i++) {
            while (j > 0 && sorted[start + i] != sorted[start + j]) {
                j = lps[j - 1];
            }

            if (sorted[start + i] == sorted[start + j]) {
                j++;
            }

            lps[i] = j;
        }

        // Search the sorted block in two cyclic copies
        j = 0;

        for (int i = 0; i < 2 * k - 1; i++) {
            int value = nums[start + (i % k)];

            while (j > 0 && value != sorted[start + j]) {
                j = lps[j - 1];
            }

            if (value == sorted[start + j]) {
                j++;
            }

            if (j == k) return true;
        }

        return false;
    }
}
