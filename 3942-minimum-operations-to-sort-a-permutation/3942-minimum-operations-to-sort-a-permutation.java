class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        if (n == 1) return 0;
        
        int indexZero = 0;
        int drops = 0;
        int rises = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                indexZero = i;
            }

            int next = nums[(i + 1) % n];

            if (nums[i] > next) {
                drops++;
            }

            if (nums[i] < next) {
                rises++;
            }
        }

        if (drops == 1 && indexZero == 0) {
            return 0;
        }

        int answer = Integer.MAX_VALUE;

        // Case 1: nums is a cyclic rotation of increasing order
        if (drops == 1) {
            int rotateLeft = indexZero;
            int reverseRotateReverse = 2 + (n - indexZero) % n;

            answer = Math.min(answer,
                    Math.min(rotateLeft, reverseRotateReverse));
        }

        // Case 2: nums is a cyclic rotation of decreasing order
        if (rises == 1) {
            int reverseIndexZero = n - 1 - indexZero;

            int reverseThenRotate = 1 + reverseIndexZero;
            int rotateThenReverse =
                    1 + (n - reverseIndexZero) % n;

            answer = Math.min(answer,
                    Math.min(reverseThenRotate, rotateThenReverse));
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}