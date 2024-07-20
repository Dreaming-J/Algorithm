import java.util.*;

class Solution {
    public int solution(int[] A, int[] B) {
        int answer = 0;
        
        Arrays.sort(A);
        Arrays.sort(B);
        
        int j = 0;
        for (int i = 0; i < B.length; i++) {
            if (B[i] > A[j]) {
                answer++;
                j++;
            }
        }
        
        return answer;
    }
}