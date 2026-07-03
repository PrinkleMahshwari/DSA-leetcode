import java.util.*;

class Solution {

    // edge representation for adjency list
    static class Edge {
        int to;
        int cost;
        
        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        
        int n = online.length;

        // build adjency list and indegree array simultaneously 
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) 
            graph.add(new ArrayList<>());
        
        int[] indegree = new int[n];

        // collect distinct edge costs for binary search
        TreeSet<Integer> uniqueCosts = new TreeSet<>();

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];
            
            graph.get(u).add(new Edge(v, cost));
            indegree[v]++;

            uniqueCosts.add(cost);
        }

        // Step 1: compute topological order once
        // since the graph is a DAG, this ordering remains valid for every binary search iteration
        List<Integer> topo = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0)
                queue.offer(i);   
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            topo.add(node);

            for (Edge edge : graph.get(node)) {
                if (--indegree[edge.to] == 0)
                    queue.offer(edge.to);
            }
        }

        // convert sorted distinct edge costs into oan rray
        int[] costs = new int[uniqueCosts.size()];
        int index = 0;

        for (int cost : uniqueCosts)
            costs[index++] = cost;
        
        // step 2: Binary search on answer
        //  search only over distinct edge costs because the answer must be equal
        // the cost of some edge present in the graph
        int left = 0;
        int right = costs.length - 1;

        int answer = -1;

        while (left <= right) {

            int mid = left + (right - left) / 2;
            int candidateScore = costs[mid];

            // if a valid path exists with minimum edge cost >= candidateScore
            if (canReach(candidateScore, graph, topo, online, k, n)) {
                answer = candidateScore;
                left = mid + 1;
            }

            // candidate score is too large
            else {
                right = mid - 1;
            }
        }

        return answer;
    }

    // DP over Topological Ordering
    // determine whether a path exists whose:
    //    every edge has cost >= minimumEdgeCost
    //    all intermediate nodes are online
    //    total recovery cost <= k
    private boolean canReach(
        int minimumEdgeCost,
        List<List<Edge>> graph,
        List<Integer> topo,
        boolean[] online,
        long k,
        int n) {

        Long INF = Long.MAX_VALUE;

        // dp[i] stores the minimum total recovery cost required to reach node i under the current binary search constraint
        long[] dp = new long[n];
        Arrays.fill(dp, INF);

        dp[0] = 0;

        // process every node exactly once in topological order
        for (int u : topo) {

            if (dp[u] == INF)
                continue;
                
            for (Edge edge : graph.get(u)) {

                int v = edge.to;

                // ignore edges that violate teh current bottleneck requirement
                if (edge.cost < minimumEdgeCost)
                    continue;
                    
                // intermediate nodes must be online
                if (v != n - 1 && !online[v])
                    continue;
                    
                // relax shortest path in DAG
                dp[v] = Math.min(dp[v], dp[u] + edge.cost);
            }
        }

        // valid path exists only if total recovery cost stays within budget
        return dp[ n - 1] <= k;
    }
}