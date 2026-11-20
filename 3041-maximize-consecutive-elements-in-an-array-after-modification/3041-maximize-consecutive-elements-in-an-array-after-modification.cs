using System;
using System.Collections.Generic;

public class Solution {
    public int MaxSelectedElements(int[] nums) {
        Array.Sort(nums);
        Dictionary<int, int> dp = new Dictionary<int, int>();
        int ans = 0;

        foreach (int num in nums) {
            int valNum = dp.GetValueOrDefault(num, 0);
            int valPrev = dp.GetValueOrDefault(num - 1, 0);

            dp[num + 1] = valNum + 1;
            dp[num] = valPrev + 1;

            ans = Math.Max(ans, Math.Max(dp[num], dp[num + 1]));
        }

        return ans;
    }
}
