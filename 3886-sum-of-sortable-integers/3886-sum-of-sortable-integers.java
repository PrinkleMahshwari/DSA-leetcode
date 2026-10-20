class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;

        int[] sorted = nums.clone ();
        Arrays.sort (sorted);

        int answer = 0;

        // only divisors of n can be sortable
        for (int k = 1; k <= n; k++) {
            if (n % k != 0) continue;

            boolean sortable = true;

            // check every block of length k
            for (int start = 0; start < n; start += k) {
                if (!isRotation (nums, sorted, start, k)) {
                    sortable = false;
                    break;
                }
            }

            if (sortable) answer += k;
        }

        return answer;
    }

    private boolean isRotation (int[] nums, int[] sorted, int start, int k) {
        // build the KMP prefix table for the sorted block
        int[] lps = new int[k];
        int j = 0;

        for (int i = 1; i < k; i++) {
            while (j > 0 && sorted[start + i] != sorted[start + j]) j = lps[j - 1];

            if (sorted[start + i] == sorted[start + j]) j++;

            lps[i] = j;
        }

        // search the sorted block in two cyclic copies
        j = 0;

        for (int i = 0; i < 2 * k - 1; i++) {
            int value = nums[start + i % k];

            while (j > 0 && value != sorted[start + j]) j = lps[j - 1];

            if (value == sorted[start + j]) j++;

            if (j == k) return true;
        }

        return false;
    }
}