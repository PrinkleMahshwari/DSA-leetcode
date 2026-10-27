using System;

public class Solution {
    public string[] CreateGrid(int m, int n) {
        string[] grid = new string[m];

        grid[0] = new string('.', n);

        for (int i = 1; i < m; i++) {
            grid[i] = new string('#', n - 1) + ".";
        }

        return grid;
    }
}
