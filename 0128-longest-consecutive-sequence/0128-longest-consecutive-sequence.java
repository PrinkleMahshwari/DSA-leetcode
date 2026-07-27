import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {

        if (nums.length == 0) return 0;

        HashSet<Integer> set = new HashSet<>();

        // Store all numbers
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : set) {

            // Check if this number is the start of sequence
            if (!set.contains(num - 1)) {

                int current = num;
                int length = 1;

                // Count consecutive numbers
                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }

                longest = Math.max(longest, length);
            }
        }

        return longest;
    }
}