import java.util.*;

class Solution {
    static final int INF = 20_000_001;
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int[][] distance = new int[n + 1][n + 1];
        for (int row = 0; row <= n; row++) {
            for (int col = 0; col <= n; col++) {
                distance[row][col] = row == col ? 0 : INF;
            }
        }
        
        for (int[] fare : fares) {
            int from = fare[0];
            int to = fare[1];
            int cost = fare[2];
            
            distance[from][to] = cost;
            distance[to][from] = cost;
        }
        
        for (int layOver = 1; layOver <= n; layOver++) {
            for (int start = 1; start <= n; start++) {
                for (int end = 1; end <= n; end++) {
                    if (start == layOver || start == end)
                        continue;
                    
                    distance[start][end] = Math.min(distance[start][layOver] + distance[layOver][end], distance[start][end]);
                }
            }
        }
        
        int answer = distance[s][a] + distance[s][b];
        for (int layOver = 1; layOver <= n; layOver++) {
            answer = Math.min(distance[s][layOver] + distance[layOver][a] + distance[layOver][b], answer);
        }
        
        return answer;
    }
    
}