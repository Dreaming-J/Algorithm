/*
#SWEA 6782. 현주가 좋아하는 제곱근 놀이
  
0. 테스트 케이스 입력
1. 정수 입력
2. 반복문을 통해 2가 될 때까지 탐색한다.
	2-1. 루트 N이 정수라면 루트 후, 다음 탐색
	2-2. 루트 N이 정수가 아니라면 1 더한 후, 다음 탐색
3. 출력
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
  
class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static final int START_NUMBER = 2;
    static int testCase;
    static long number;
    static int count;
  
    public static void main(String args[]) throws IOException {
    	
        //0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());
        for(int tc = 1; tc <= testCase; tc++) {
        	//1. 정수 입력
        	number = Long.parseLong(input.readLine().trim());
        	count = 0;
        	
        	//2. 반복문을 통해 2가 될 때까지 탐색한다.
        	while (number != 2L) {
        		double sqrt = Math.sqrt(number);
        		
        		//2-1. 루트 N이 정수라면 루트 후, 다음 탐색
        		if (sqrt == (long) sqrt) {
        			number = (long) sqrt;
            		count++;
        			continue;
        		}
        		
        		//2-2. 루트 N이 정수가 아니라면 다음 제곱까지 더한 후, 다음 탐색
        		long next = ((long) sqrt + 1) * ((long) sqrt + 1);
        		count += next - number;
        		number = next;
        	}
        	
        	//3. 출력
            output.append("#").append(tc).append(" ").append(count).append("\n");
        }
        System.out.println(output);
    }
}