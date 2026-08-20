import java.util.*;

class Solution {
    public int[] resultArray(int[] nums) {
        
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();

        // first two operations are fixed
        arr1.add(nums[0]);
        arr2.add(nums[1]);

        int last1 = nums[0];
        int last2 = nums[1];

        // process remaining elements
        for (int i = 2; i < nums.length; i++) {

            if (last1 > last2) {
                arr1.add(nums[i]);
                last1 = nums[i];
            } else {
                arr2.add(nums[i]);
                last2 = nums[i];
            }
        }

        // concatenate arr1 and arr2
        int[] result = new int[nums.length];

        int index = 0;

        for (int num : arr1)
            result[index++] = num;
        
        for (int num : arr2)
            result[index++] = num;
        
        return result;
    }
}