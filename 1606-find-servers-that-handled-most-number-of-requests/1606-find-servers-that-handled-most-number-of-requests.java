class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> available = new TreeSet<>();

        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare (a[0], b[0])
        );

        int[] count = new int[k];

        // initially all servers are available
        for (int i = 0; i < k; i++) available.add (i);

        // process every request
        for (int i = 0; i < arrival.length; i++) {
            // release servers that have completed their requests
            while (!busy.isEmpty() && busy.peek ()[0] <= arrival[i]) {
                long[] server = busy.poll ();
                available.add ((int) server[1]);
            }

            // all servers are busy
            if (available.isEmpty ()) continue;

            // find the next available server
            int start = i % k;

            Integer server = available.ceiling (start);

            // wrap around if necessary
            if (server == null) server = available.first ();

            // assign the request
            available.remove (server);

            count[server]++;

            long finishTime = (long) arrival[i] + load[i];

            busy.offer (new long[]{finishTime, server});
        }

        // fin the max number of handled requests
        int maxRequests = 0;
        for (int requests : count) maxRequests = Math.max (maxRequests, requests);

        // collect all busiest servers
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) 
            if (count[i] == maxRequests) result.add (i);
        
        return result;
    }
}