import java.util.*;
import java.util.stream.*;

class Solution {
    List<String> answer;
    
    public String[] solution(String[][] tickets) {
        answer = new ArrayList<>();
        boolean[] visited = new boolean[tickets.length];
        List<String> route = new ArrayList<>();
        
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i][0].equals("ICN")) {
                visited[i] = true;
                route.clear();
                route.add(tickets[i][0]);
                route.add(tickets[i][1]);
                dfs(route, i, tickets, visited);
                visited[i] = false;
            }
        }
        
        return answer.stream()
            .toArray(String[]::new);
    }
    
    public void dfs (List<String> route, int idx, String[][] tickets, boolean[] visited) {
        if (route.size() - 1 == tickets.length && answer.toString().compareTo(route.toString()) > 0) {
            answer.clear();
            answer.addAll(route);
            return;
        }
        
        for (int i = 0; i < tickets.length; i++) {
            if (!visited[i] && route.get(route.size() - 1).equals(tickets[i][0])) {
                visited[i] = true;
                route.add(tickets[i][1]);
                dfs(route, i, tickets, visited);
                route.remove(route.size() - 1);
                visited[i] = false;
            }
        }
    }
}