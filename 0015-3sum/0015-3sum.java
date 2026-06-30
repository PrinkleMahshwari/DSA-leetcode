class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        
        // store final list of distinct triplets
        List<List<Integer>> result = new ArrayList<>();

        // sort the array to use two pointers techinque and skip duplicates easily
        Arrays.sort(nums);

        // traverse every number to fix the first element of the triplet
        for (int i = 0; i < nums.length - 2; i++) {

            // early termination: if current number is positive, sum can never be zro
            if (nums[i] > 0)
                break;

            // skip duplicate values for the first element of the triplet
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            
            // track the remaining two elements using two pointers
            int left = i + 1;
            int right = nums.length - 1;

            // look for pairs that sum up to target value
            while (left < right) {

                // calcualte current sum of the triplet
                int sum = nums[i] + nums[left] + nums[right];

                // triplet found
                if (sum == 0) {

                    // add valid triplet to the result list using faster custom list creation
                    result.add(new ArrayList<>(Arrays.asList(nums[i], nums[left], nums[right])));

                    // move both pointers
                    left++;
                    right--;
                    
                    // skip duplicate values for the second
                    while (left < right && nums[left] == nums[left - 1])
                        left++;
                    
                    //skip duplicate values for third element
                    while (left < right && nums[right] == nums[right + 1])
                        right--;
                    
                }
                // sum is too small, move left pointer to increase sum
                else if (sum < 0) {
                    left++;
                }
                // sum is too large, move right pointer to decrease sum
                else {
                    right--;
                }
            }
        }

        // return final list of triplets
        return result;
    }
}