import java.util.ArrayList;
import java.util.List;

class Solution {

    // store the minimum road weight encountered inside the connected component
    private int minimumScore = Integer.MAX_VALUE;

    public int minScore(int n, int[][] roads) {

        // adjency list
        // each entry stores (neightbor, roadDistance)
        List<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++)
            graph[i] = new ArrayList<>();
        
        // build the undirected graph
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int distance = road[2];

            graph[u].add(new int[]{v, distance});
            graph[v].add(new int[]{u, distance});
        }

        // track the visited cities to avoid revisting them
        boolean[] visited = new boolean[n + 1];

        // traverse only the connected component containing city 1
        dfs(1, graph, visited);

        // minimum road weight inside this component 
        // is the minimum possible score between city 1 and cit n
        return minimumScore;
    }

    // Depth-First-Search  (DFS)
    // explore the entire connected component starting from city 1
    private void dfs(int city, List<int[]>[] graph, boolean[] visited) {

        visited[city] = true;

        // inspect every road leaving the current city
        for (int[] edge : graph[city]) {

            int neighbor = edge[0];
            int roadDistance = edge[1];

            // update the global minimum road weight
            minimumScore = Math.min(minimumScore, roadDistance);

            // continue exploring unvisited neighboring cities
            if (!visited[neighbor])
                dfs(neighbor, graph, visited);
        }
    }
}