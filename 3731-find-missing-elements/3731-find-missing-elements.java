class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        int n = nums.length;
        // this is finding min and max in one pass
        int min = nums[0], max = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] < min) min = nums[i];
            if (nums[i] > max) max = nums[i];
        }

        // this is using a boolean array for O(1) lookup
        boolean[] present = new boolean[max - min + 1];
        for (int num : nums) {
            present[num - min] = true;
        }

        // this is collecting missing elements in sorted order
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < present.length - 1; i++) {
            if (!present[i]) {
                result.add(i + min); // this is converting back to actual value
            }
        }

        return result;
    }
}
