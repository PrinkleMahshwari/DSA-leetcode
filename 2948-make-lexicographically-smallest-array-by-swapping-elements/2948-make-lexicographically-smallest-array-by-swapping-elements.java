import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {

        int n = nums.length;

        // store (value, original index)
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }        

        // sort by value
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int[] result = new int[n];

        int start = 0;
        while (start < n) {

            int end = start;

            // find one connected component
            while (end + 1 < n && (long) arr[end + 1][0] - arr[end][0] <= limit)
                end++;
            
            // collect original indices
            int size = end - start + 1;
            int[] indices = new int[size];

            for (int i = 0; i < size; i++)
                indices[i] = arr[start + i][1];
            
            // original pos must be processed in ascending order
            Arrays.sort(indices);

            // values are already sorted
            for (int i = 0; i < size; i++)
                result[indices[i]] = arr[start + i][0];
            
            start = end + 1;

        }

        return result;
    }
}