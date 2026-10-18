using System;
using System.Collections.Generic;

public class Solution {
    private const long MOD = 1_000_000_007L;
    private long[][] comb;

    public int NumOfWays(int[] nums) {
        int n = nums.Length;

        // Precompute combinations using Pascal's triangle
        comb = new long[n + 1][];
        for (int i = 0; i <= n; i++) {
            comb[i] = new long[n + 1];
            comb[i][0] = 1;
            comb[i][i] = 1;

            for (int j = 1; j < i; j++) {
                comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
            }
        }

        List<int> list = new List<int>(nums);

        // Subtract the original ordering
        return (int)((Solve(list) - 1 + MOD) % MOD);
    }

    private long Solve(List<int> list) {
        int len = list.Count;
        if (len <= 2) return 1;

        int root = list[0];
        List<int> left = new List<int>();
        List<int> right = new List<int>();

        for (int i = 1; i < len; i++) {
            if (list[i] < root) left.Add(list[i]);
            else right.Add(list[i]);
        }

        // Ways to interleave left and right subtree elements
        long ways = comb[len - 1][left.Count];

        // Multiply by valid reorderings of each subtree
        ways = (ways * Solve(left)) % MOD;
        ways = (ways * Solve(right)) % MOD;

        return ways;
    }
}
