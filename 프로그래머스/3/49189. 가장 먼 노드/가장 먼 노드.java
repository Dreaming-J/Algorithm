import java.util.*;

class Solution {
    List<Integer>[] graph;
    int answer;
    
    public int solution(int n, int[][] edge) {
        makeGraph(n, edge);
        
        findRoute(n);
        
        return answer;
    }
    
    public void makeGraph(int n, int[][] edge) {
        graph = new ArrayList[n + 1];
        for (int idx = 1; idx <= n; idx++)
            graph[idx] = new ArrayList<>();
        
        for (int[] e : edge) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
    }
    
    public void findRoute(int n) {
        boolean[] visited = new boolean[n + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        
        visited[1] = true;
        queue.add(1);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            answer = size;
            
            while (size-- > 0) {
                int cur = queue.poll();
                
                for (int next : graph[cur]) {
                    if (visited[next])
                        continue;
                    
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }
    }
}