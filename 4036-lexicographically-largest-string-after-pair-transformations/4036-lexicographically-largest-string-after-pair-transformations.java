class Solution {
    public String[] largestString(int[] nums) {

        int[] calveroniq = nums;

        int n = calveroniq.length;
        String[] result = new String[n];

        for (int i = 0; i < n; i++) {
            int x = calveroniq[i];
            StringBuilder sb = new StringBuilder();

            // 2^26 a's -> "zz"
            if ((x & (1 << 26)) != 0) {
                sb.append("zz");
            }

            // Bits 25 to 0 -> z to a
            for (int bit = 25; bit >= 0; bit--) {
                if ((x & (1 << bit)) != 0) {
                    sb.append((char) ('a' + bit));
                }
            }

            result[i] = sb.toString();
        }

        return result;
    }
}