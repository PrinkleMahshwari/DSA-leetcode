using System.Collections.Generic;

public class Solution {
    public IList<int> TargetIndices(int[] nums, int target) {
        int smaller = 0;
        int equal = 0;

        foreach (int num in nums) {
            if (num < target) smaller++;
            else if (num == target) equal++;
        }

        List<int> result = new List<int>();
        for (int i = smaller; i < smaller + equal; i++) {
            result.Add(i);
        }

        return result;
    }
}
