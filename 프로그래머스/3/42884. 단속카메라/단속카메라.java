import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        int answer = 0;
        
        Arrays.sort(routes, Comparator.comparing((int[] o) -> o[1]));
        
        int max = -30_000;
        for (int i = 0; i < routes.length; i++) {
            if (routes[i][0] > max) {
                answer++;
                max = routes[i][1];
            }
        }
        
        return answer;
    }
}