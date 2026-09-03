class Solution {
    public boolean uniformArray(int[] nums1) {
        int smallestOdd = Integer.MAX_VALUE;
        int smallestEven = Integer.MAX_VALUE;

        for (int num : nums1) {
            if ((num & 1) == 0)
                smallestEven = Math.min(smallestEven, num);
            else
                smallestOdd = Math.min(smallestOdd, num);
        }

        // all elements already have the same parity
        if (smallestOdd == Integer.MAX_VALUE || smallestEven == Integer.MAX_VALUE)
            return true;
        
        // mixed parity: make everything odd
        return smallestOdd < smallestEven;
    }
}