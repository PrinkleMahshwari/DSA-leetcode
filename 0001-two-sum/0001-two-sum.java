import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // create a hash map to store numbers and thier corresponding indices
        Map<Integer, Integer> numMap = new HashMap<>();
        // itertrate through the array
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // if complement exists in the map, we found our pair
            if (numMap.containsKey(complement)) {
                return new int[] { numMap.get(complement), i};
            }
            // otherwise, store the current number and it's index i in the map
            numMap.put(nums[i], i);
        }
        // return an empty array if no solution is found 
        return new int[] {};
    }
}