class Solution {
    private int gcd (int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
    public int maxLength(int[] nums) {
        int n = nums.length, ans = -1;

        for (int i = 0; i < n; i++) {
            long product = 1;
            int g = 0, l = 1;

            for (int j = i; j < n; j++) {
                int num = nums[j];
                product *= num;
                g = gcd (g, num);
                l = l / gcd (l, num) * num;
                if (product > 25200) break;
                if (product == (long) g * l) ans = Math.max (ans, j - i + 1);
            }
        }

        return ans;
    }
}