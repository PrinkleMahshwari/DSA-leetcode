class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        Arrays.sort (arr2);

        int count = 0;

        for (int num : arr1) {
            int low = 0;
            int high = arr2.length;

            // find the first element >= num - d
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (arr2[mid] < num - d) low = mid + 1;
                else high = mid;
            }

            // check whether an element lies within [num - d, num + d]
            if (low == arr2.length || arr2[low] > num + d) count++;
        }

        return count;
    }
}