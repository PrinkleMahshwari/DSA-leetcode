using System;
using System.Collections.Generic;

public class Solution {
    public int MinSwaps(int[] nums) {
        int n = nums.Length;

        // Store element info: (Value, DigitSum, OriginalIndex)
        var arr = new List<(int val, int dSum, int origIdx)>(n);
        for (int i = 0; i < n; i++) {
            arr.Add((nums[i], DigitSum(nums[i]), i));
        }

        // Sort by digit sum, then by value
        arr.Sort((a, b) => {
            if (a.dSum != b.dSum) return a.dSum.CompareTo(b.dSum);
            return a.val.CompareTo(b.val);
        });

        // Build permutation map
        int[] permutation = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[arr[i].origIdx] = i;
        }

        // Count permutation cycles
        bool[] visited = new bool[n];
        int cycles = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            cycles++;

            int current = i;
            while (!visited[current]) {
                visited[current] = true;
                current = permutation[current];
            }
        }

        return n - cycles;
    }

    private int DigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
