class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        // sort logs by requests
        Arrays.sort (logs, (a, b) -> Integer.compare (a[1], b[1]));

        int m = queries.length;
        // store query time and original index
        int[][] sortedQueries = new int[m][2];
        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        // sort queries by time
        Arrays.sort (sortedQueries, (a, b) -> Integer.compare (a[0], b[0]));
        
        int[] answer = new int[m];
        int[] freq = new int[n + 1];
        int start = 0, end = 0, active = 0;

        // process queries in sorted order
        for (int[] query : sortedQueries) {
            int q = query[0], index = query[1];
            int left = q - x;

            // add logs with time <= q
            while (end < logs.length && logs[end][1] <= q) {
                int server = logs[end][0];
                if (freq[server] == 0) active++;
                freq[server]++;
                end++;
            }

            // removing logs with time  < left
            while (start < end && logs[start][1] < left) {
                int server = logs[start][0];
                freq[server]--;
                if (freq[server] == 0) active--;
                start++;
            }

            // store answer at original index
            answer[index] = n - active;
        }

        return answer;
    }
}