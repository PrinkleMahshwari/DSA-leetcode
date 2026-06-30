class Solution {
    public int threeSumClosest(int[] nums, int target) {
        
        // sort the array to use two pointers technique efficiently
        Arrays.sort(nums);

        // store the closest sum found so far, initialized wuth the first possible triplet
        int closestSum = nums[0] + nums[1] + nums[2];

        // traverse every number to fix the first element of the triplet
        for (int i = 0; i < nums.length - 2; i++) {

            // skip duplicate values for the first element to optimize runtime
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            
            // micro-optimization: find the smallest possible sum for the current fixed index i
            int minSum = nums[i] + nums[i + 1] + nums[i + 2];

            // if the smallest possible sum is already larger than target , check closeness and break loop
            if (minSum > target) {
                if (Math.abs(minSum - target) < Math.abs(closestSum - target))
                    closestSum = minSum;
                
                break;
            }
            
            // micro-optimization: find the larget possible sum for the current fixed index i
            int maxSum = nums[i] + nums[nums.length - 2] + nums[nums.length - 1];

            // if the largest possible sum i still smaller than target, check closeness and skip while loop
            if (maxSum < target) {
                if (Math.abs(maxSum - target) < Math.abs(closestSum - target))
                    closestSum = maxSum;
                
                continue;
            }
            
            // track the remaining two elements using two pointers
            int left = i + 1;
            int right = nums.length - 1;

            // look for pairs that get closest to the target value
            while (left < right) {

                // calculate the current sume of the triplet
                int currentSum = nums[i] + nums[left] + nums[right];

                // early termination: if current sum perfectly matches target return it immediately
                if (currentSum == target)
                    return currentSum;
                
                // check whether the current sum is closer to the target than the previously stored closest sum
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target))
                    closestSum = currentSum;

                // current sum is smaller than target, move left pointer to increase sum
                if (currentSum < target)
                    left++;

                // current sum is larger than target, move right pointer to decrease sum
                else
                    right--;
            
            }
        }

        // return the final closest sum
        return closestSum;
    }
}