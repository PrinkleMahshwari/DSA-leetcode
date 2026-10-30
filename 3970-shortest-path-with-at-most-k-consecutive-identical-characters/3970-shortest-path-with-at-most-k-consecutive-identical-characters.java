import java.util.*;

class Solution {

    static class State {
        int node;
        int count;
        int distance;

        State(int node, int count, int distance) {
            this.node = node;
            this.count = count;
            this.distance = distance;
        }
    }

    public int shortestPath(int n, int[][] edges, String labels, int k) {

        // Build the directed graph
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            graph[u].add(new int[]{v, w});
        }

        // dist[node][count] = minimum cost to reach this state
        int[][] dist = new int[n][k + 1];

        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<State> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.distance, b.distance)
        );

        // Start at node 0 with a consecutive count of 1
        dist[0][1] = 0;
        pq.offer(new State(0, 1, 0));

        while (!pq.isEmpty()) {

            State current = pq.poll();

            int u = current.node;
            int count = current.count;
            int cost = current.distance;

            // Ignore outdated states
            if (cost != dist[u][count]) {
                continue;
            }

            // Reached the destination with minimum valid cost
            if (u == n - 1) {
                return cost;
            }

            // Explore outgoing edges
            for (int[] edge : graph[u]) {

                int v = edge[0];
                int weight = edge[1];

                int nextCount;

                if (labels.charAt(u) == labels.charAt(v)) {
                    nextCount = count + 1;
                } else {
                    nextCount = 1;
                }

                // Reject invalid consecutive characters
                if (nextCount > k) {
                    continue;
                }

                int newCost = cost + weight;

                if (newCost < dist[v][nextCount]) {

                    dist[v][nextCount] = newCost;

                    pq.offer(
                        new State(v, nextCount, newCost)
                    );
                }
            }
        }

        return -1;
    }
}
