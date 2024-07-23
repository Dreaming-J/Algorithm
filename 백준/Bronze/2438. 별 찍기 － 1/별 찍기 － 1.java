import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
#백준 2438. 별 찍기 - 1
N번째 줄에 별 N개를 찍는 문제

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
    	
    	for (int row = 1; row <= lineCount; row++) {
    		for (int col = 0; col < row; col++)
    			answer.append("*");
    		answer.append("\n");
    	}
    	
    	System.out.println(answer.toString());
    }
}
