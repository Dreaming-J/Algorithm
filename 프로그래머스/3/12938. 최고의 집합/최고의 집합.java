import java.util.*;
import java.util.stream.*;

class Solution {
    public int[] solution(int n, int s) {
        if (s / n == 0)
            return new int[]{-1};
        
        int[] answer = new int[n];
        Arrays.fill(answer, s/n);
        
        int remainder = s % n;
        while (remainder > 0)
            answer[n - remainder--]++;
        
        return answer;
    }
}