class Solution {
    private static final long MOD = 1_000_000_007L;
    private long[][] comb;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        // precompute combinations using pascal's triangle
        comb = new long[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            comb[i][0] = 1;
            comb[i][i] = 1;

            for (int j = 1; j < i; j++) comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
        }    

        List<Integer> list = new ArrayList<>();

        for (int num : nums) list.add(num);

        // subtract the original ordering
        return (int) ((solve(list) - 1 + MOD) % MOD);
    }

    private long solve (List<Integer> nums) {
        int n = nums.size();

        if (n <= 2) return 1;
        
        int root = nums.get(0);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            int num = nums.get(i);

            if (num < root) left.add(num);
            else right.add(num);
        }

        // ways to interleave left and right subtree elements
        long ways = comb[n - 1][left.size()];

        // mutliply by valid reorderings of each subtree
        ways = ways * solve (left) % MOD;
        ways = ways * solve (right) % MOD;

        return ways;
    }
}