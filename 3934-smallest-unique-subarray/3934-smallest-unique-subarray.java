class Solution {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE = 911382323L;
    private long[] prefix1, prefix2, pow1, pow2;

    private long getHash (int start, int len, long[] prefix, long[] power, long mod) {
        return (prefix[start + len] - prefix[start] * power[len] % mod + mod) % mod;
    }

    private boolean check (int len, int n) {
        HashMap<Long, Integer> freq = new HashMap<>();
        for (int i = 0; i + len <= n; i++) {
            long h1 = getHash (i, len, prefix1, pow1, MOD1);
            long h2 = getHash (i, len, prefix2, pow2, MOD2);
            long hash = (h1 << 32) | h2;
            freq.put (hash, freq.getOrDefault (hash, 0) + 1);
        }

        for (int count : freq.values ())
            if (count == 1) return true;
        
        return false;
    }

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        prefix1 = new long[n + 1];
        prefix2 = new long[n + 1];
        pow1 = new long[n + 1];
        pow2 = new long[n + 1];
        pow1[0] = pow2[0] = 1;

        for (int i = 0; i < n; i++) {
            pow1[i + 1] = pow1[i] * BASE % MOD1;
            pow2[i + 1] = pow2[i] * BASE % MOD2;
            prefix1[i + 1] = (prefix1[i] * BASE + nums[i]) % MOD1;
            prefix2[i + 1] = (prefix2[i] * BASE + nums[i]) % MOD2;
        }    

        int left = 1, right = n, ans = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check (mid, n)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }
}