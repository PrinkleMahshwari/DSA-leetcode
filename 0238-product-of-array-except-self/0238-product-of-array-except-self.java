class Solution {
    public int[] productExceptSelf(int[] nums) {

        int n = nums.length;
        int[] answer = new int[n];

        // answer[i] first holds the product of everything to its LEFT.
        // answer[0] has no left elements, so it stays the identity value 1
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        // second pass: sweep from the right, multiply in the running
        // suffix product on the fly instead of storing a separate suffix
        // array -- this is what gets us to O(1) extra space (output array
        // doesn't count per the problem's own space rule)
        int suffixProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffixProduct;
            suffixProduct *= nums[i];
        }

        return answer;
    }
}