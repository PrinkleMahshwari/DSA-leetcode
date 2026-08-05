class Solution:
    def remainingMethods(self, n: int, k: int, invocations: list[list[int]]) -> list[int]:
        # Build graph
        graph = [[] for _ in range(n)]
        for u, v in invocations:
            graph[u].append(v)
            
        # Find suspicious methods
        suspicious = [False] * n
        
        def dfs(node: int):
            suspicious[node] = True
            for neighbor in graph[node]:
                if not suspicious[neighbor]:
                    dfs(neighbor)
                    
        dfs(k)
        
        # Check if suspicious methods are called externally
        for u, v in invocations:
            if not suspicious[u] and suspicious[v]:
                return list(range(n))
                
        # Remove suspicious methods
        return [i for i in range(n) if not suspicious[i]]
