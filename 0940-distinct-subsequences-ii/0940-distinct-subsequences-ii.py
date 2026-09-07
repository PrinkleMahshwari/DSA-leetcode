class Solution:
    def distinctSubseqII(self, s: str) -> int:
        MOD = 1_000_000_007

        dp = 1
        # Properly initialized 26-element array for tracking last positions
        last = [0] * 26

        for c in s:
            index = ord(c) - ord('a')

            previous = dp
            # Python natively handles negative modulo, but keeping explicit math for safety
            dp = (2 * dp - last[index] + MOD) % MOD;
            last[index] = previous

        return (dp - 1 + MOD) % MOD
