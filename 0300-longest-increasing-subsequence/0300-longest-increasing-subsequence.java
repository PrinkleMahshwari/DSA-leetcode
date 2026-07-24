import java.util.Arrays;

class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        // tails[i] stores the smallest tail of all increasing subsequences of length i + 1
        int[] tails = new int[nums.length];
        int len = 0;
        
        for (int num : nums) {
            // Binary search to find the insertion point of num in the active tails list
            int idx = Arrays.binarySearch(tails, 0, len, num);
            
            // If the element isn't found, calculate its positive insertion point index
            if (idx < 0) {
                idx = -(idx + 1);
            }
            
            // Update or append the element at its calculated position
            tails[idx] = num;
            
            // If it was appended to the very end, expand our active length counter
            if (idx == len) {
                len++;
            }
        }
        
        return len;
    }
}
