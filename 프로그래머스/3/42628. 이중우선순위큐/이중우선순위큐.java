import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];
        
        PriorityQueue<Integer> maxQ = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> minQ = new PriorityQueue<>();
        
        for (String operation : operations) {
            if (operation.charAt(0) == 'I') {
                maxQ.offer(Integer.parseInt(operation.substring(2, operation.length())));
            }
            else if (!maxQ.isEmpty()) {
                if (operation.equals("D 1"))
                    maxQ.poll();
                if (operation.equals("D -1")) {
                    minQ.clear();
                    minQ.addAll(maxQ);
                    minQ.poll();
                    maxQ.clear();
                    maxQ.addAll(minQ);
                }
            }
        }
        
        if (!maxQ.isEmpty()) {
            minQ.clear();
            minQ.addAll(maxQ);
            answer[0] = maxQ.poll();
            answer[1] = minQ.poll();
        }
        
        return answer;
    }
}