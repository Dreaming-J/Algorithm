import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
#백준 2441. 별 찍기 - 4
첫째 줄에 N개, 둘째 줄에 N-1개, ..., N번째 줄에 1개의 별을 오른쪽 정렬하여 찍는 문제

#입력
첫째 줄: N (1 <= N <= 100)
#출력
첫째 줄부터 N번째 줄까지 차례대로 별 출력

#로직
이중 for문으로 별 출력
 */

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder answer = new StringBuilder();
    static int lineCount;
    
    public static void main(String[] args) throws IOException {
    	lineCount = Integer.parseInt(br.readLine());
    	
    	for (int row = 0; row < lineCount; row++) {
    		for (int col = 0; col < lineCount; col++) {
    			if (col < row) {
    				answer.append(" ");
    				continue;
    			}
    			answer.append("*");
    		}
    		answer.append("\n");
    	}
    	
    	System.out.println(answer.toString());
    }
}
