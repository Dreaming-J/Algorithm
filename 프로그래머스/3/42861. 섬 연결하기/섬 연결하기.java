import java.util.*;

class Solution {
    public static int find(int[] islands, int no) {
        if (islands[no] == no) return no;
        return find(islands, islands[no]);
    }
    
    public static void union(int[] islands, int island1, int island2) {
        int parent1 = find(islands, island1);
        int parent2 = find(islands, island2);
        islands[parent1] = parent2;
    }
    
    public int solution(int n, int[][] costs) {
        int answer = 0;
        
        Arrays.sort(costs, Comparator.comparing((int[] o) -> o[2]));
        
        int[] islands = new int[n];
        Arrays.setAll(islands, num -> num);
        
        for (int[] cost : costs) {
            if (find(islands, cost[0]) == find(islands, cost[1])) continue;
            union(islands, cost[0], cost[1]);
            answer += cost[2];
        }
        
        return answer;
    }
}