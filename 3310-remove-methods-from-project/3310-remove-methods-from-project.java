import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        List<Integer> result = new ArrayList<>();

        // Build graph
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : invocations) {
            graph[edge[0]].add(edge[1]);
        }


        // Find suspicious methods
        boolean[] suspicious = new boolean[n];

        dfs(k, graph, suspicious);


        // Check if suspicious methods are called externally
        for (int[] edge : invocations) {

            int from = edge[0];
            int to = edge[1];

            if (!suspicious[from] && suspicious[to]) {

                // Cannot remove anything
                for (int i = 0; i < n; i++) {
                    result.add(i);
                }

                return result;
            }
        }


        // Remove suspicious methods
        for (int i = 0; i < n; i++) {

            if (!suspicious[i]) {
                result.add(i);
            }
        }

        return result;
    }


    private void dfs(int node, List<Integer>[] graph, boolean[] visited) {

        visited[node] = true;


        for (int next : graph[node]) {

            if (!visited[next]) {
                dfs(next, graph, visited);
            }
        }
    }
}