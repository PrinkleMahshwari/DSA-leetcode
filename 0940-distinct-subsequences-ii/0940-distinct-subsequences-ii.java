class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1_000_000_007L;

        long dp = 1;
        long[] last = new long[26];

        for (char c : s.toCharArray()) {
            int index = c - 'a';

            long previous = dp;
            dp = (2 * dp - last[index] + MOD) % MOD;
            last[index] = previous;
        }

        return (int) ((dp - 1 + MOD) % MOD);
    }
}