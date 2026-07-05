import java.util.*;

class Solution {

    private static final int MOD = 1_000_000_007;

    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();

        // convert to char array grid once, avoids repeated charAt() calls
        char[][] grid = new char[n][n];
        for (int i = 0; i < n; i++) {
            grid[i] = board.get(i).toCharArray();
        }

        // maxSum[i][j] = best score reachable from (i, j) to E
        // ways[i][j]  = number of paths achieving that best score
        // -1 sentinel marks "unreachable"
        long[][] maxSum = new long[n][n];
        long[][] ways = new long[n][n];
        for (long[] row : maxSum) Arrays.fill(row, -1);

        // base case: S has score 0, exactly 1 way (standing still)
        maxSum[n - 1][n - 1] = 0;
        ways[n - 1][n - 1] = 1;

        // predecessor offsets: down, right, down-right
        // reverse of the allowed moves (up, left, up-left)
        int[][] predecessors = {{1, 0}, {0, 1}, {1, 1}};

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                if (grid[i][j] == 'X') continue;
                if (i == n - 1 && j == n - 1) continue;

                long best = -1;
                long count = 0;

                // single loop keeps best and count in sync at every step,
                // this is the key fix: when a strictly better predecessor
                // shows up, count is REPLACED, not added to
                for (int[] d : predecessors) {
                    int pi = i + d[0];
                    int pj = j + d[1];

                    if (pi >= n || pj >= n || maxSum[pi][pj] == -1) continue;

                    if (maxSum[pi][pj] > best) {
                        best = maxSum[pi][pj];
                        count = ways[pi][pj]; // replace, not accumulate
                    } else if (maxSum[pi][pj] == best) {
                        count += ways[pi][pj]; // genuine tie, accumulate
                    }
                }

                if (best == -1) continue;

                long cellValue = (grid[i][j] == 'E') ? 0 : (grid[i][j] - '0');

                maxSum[i][j] = best + cellValue;
                ways[i][j] = count % MOD;
            }
        }

        if (maxSum[0][0] == -1) {
            return new int[]{0, 0};
        }

        return new int[]{(int) maxSum[0][0], (int) ways[0][0]};
    }
}