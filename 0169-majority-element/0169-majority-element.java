class Solution {
    public int majorityElement(int[] nums) {
        
        // Boyer-Moore voting: candidate cancels out against every non-matching
        // element. since a true majority element appears more than n/2 times,
        // it can never be fully cancelled out, so whatever survives is the answer
        int candidate = nums[0];
        int count = 0;

        for (int num : nums) {
            if (count == 0)
                candidate = num;
            
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }
}