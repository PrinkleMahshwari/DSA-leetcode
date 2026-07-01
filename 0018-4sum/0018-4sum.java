class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        
        List<List<Integer>> result = new ArrayList<>();

        // Step 1: sort the array to use two pointers technique
        Arrays.sort(nums);

        int n = nums.length;

        // Step 2 : fixt first element
        for (int i = 0; i < n; i++) {

            // skip duplicate values for i
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            
            // Step 3: fix second element
            for (int j = i + 1; j < n - 2; j++) {

                // skip duplicate values for j
                if (j > i + 1 && nums[j] == nums[j - 1])
                    continue;
                
                // remaining target after fixing i and j
                long newTarget = (long) target - nums[i] - nums[j];

                int left = j + 1;
                int right = n - 1;

                // Step 4: two pointer search
                while (left < right) {
                    long sum = (long) nums[left] + nums[right];

                    if (sum == newTarget) {

                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // skip duplicate values for left
                        while (left < right && nums[left] == nums[left + 1])
                            left++;
                        
                        // skip duplicate values for right
                        while (left < right && nums[right] == nums[right - 1])
                            right--;
                        
                        left++;
                        right--;
                    }
                    else if (sum < newTarget)
                        left++;
                    
                    else
                        right--;
                }
            }
        }

        return result;
    }
}