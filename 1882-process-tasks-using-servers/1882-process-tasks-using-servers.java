class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        // Available Servers: [weight, index]
        PriorityQueue<int[]> available = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) return Integer.compare (a[0], b[0]);
                return Integer.compare (a[1], b[1]);
            }
        );

        // Busy Severs: [freeTime, weight, index] 
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) return Long.compare (a[0], b[0]);
                if (a[1] != b[1]) return Long.compare (a[1], b[1]);
                return Long.compare (a[2], b[2]);
            }
        );

        int n = servers.length, m = tasks.length;
        int[] ans = new int[m];
        // initially all servers are available
        for (int i = 0; i < n; i++) available.offer (new int[]{servers[i], i});

        long time = 0;
        for (int j = 0; j < m; j++) {
            // current time can't be earlier than task arrival
            time = Math.max (time, j);
            // release all servers that have completed all their tasks
            while (!busy.isEmpty() && busy.peek ()[0] <= time) {
                long[] server = busy.poll ();
                available.offer (new int[]{(int) server[1], (int) server[2]});
            }

            // if no server is available, wait for the earliest one
            if (available.isEmpty()) {
                time = busy.peek ()[0];
                // release all servers available at this time
                while (!busy.isEmpty () && busy.peek ()[0] <= time) {
                    long[] server = busy.poll ();
                    available.offer (new int[]{(int) server[1], (int) server[2]});
                }
            }

            // select the best available server
            int[] server = available.poll ();
            int weight = server[0], index = server[1];
            ans[j] = index;
            // assign the task and update servers freeTime
            long freeTime = time + tasks[j];
            busy.offer (new long[]{freeTime, weight, index});
        }

        return ans;
    }
}