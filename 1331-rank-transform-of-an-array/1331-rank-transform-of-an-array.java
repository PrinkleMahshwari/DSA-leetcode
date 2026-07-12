import java.util.*;

class Solution {

    public int[] arrayRankTransform(int[] arr) {
        int n = arr.length;

        if (n == 0) return arr;

        // sort indices by value instead of sorting values + using a HashMap.
        // this avoids all Integer boxing/hashing overhead that HashMap<Integer,Integer>
        // incurs -- every put/get there boxes an int into an Integer object and computes
        // a hash, which adds up heavily for n up to 10^5
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;

        Arrays.sort(indices, (a, b) -> arr[a] - arr[b]);

        int[] result = new int[n];
        int rank = 1;

        // walk sorted indices, assign rank directly into result[originalIndex],
        // only bump rank when the value actually changes from the previous one
        result[indices[0]] = rank;
        for (int i = 1; i < n; i++) {
            if (arr[indices[i]] != arr[indices[i - 1]]) {
                rank++;
            }
            result[indices[i]] = rank;
        }

        return result;
    }
}