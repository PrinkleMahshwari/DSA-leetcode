class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int currentSum = numbers[left] + numbers[right];
            
            if (currentSum == target) {
                return new int[]{left + 1, right + 1};
            } else if (currentSum < target) {
                left++; // Sum is too small, move to a larger number
            } else {
                right--; // Sum is too large, move to a smaller number
            }
        }
        
        return new int[]{-1, -1}; // Fallback (guaranteed to not hit based on constraints)
    }
}
