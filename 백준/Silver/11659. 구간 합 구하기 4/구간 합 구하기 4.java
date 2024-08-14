import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
=백준 11659. 구간 합 구하기 4

1. 입력
	1-1. 수의 개수와 구갑 합을 구하는 횟수를 입력받는다.
	1-2. 개수만큼 수를 입력받는다.
		1-2-1. 구간 합을 구한다.
	1-3. 구갑 합을 계산할 시작 구간과 끝 구간을 횟수만큼 입력받는다.
2. 구간 합의 끝 구간에서 시작 구간까지를 뺀다.
 */
public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static int numberSize, count;
	static int[] number;
	
	public static void main(String[] args) throws IOException {
		//1. 입력
		//1-1. 수의 개수와 구갑 합을 구하는 횟수를 입력받는다.
		st = new StringTokenizer(input.readLine());
		numberSize = Integer.parseInt(st.nextToken());
		count = Integer.parseInt(st.nextToken());
		
		number = new int[numberSize + 1];
		
		//1-2. 개수만큼 수를 입력받는다.		
		st = new StringTokenizer(input.readLine());
		for (int idx = 1; idx <= numberSize; idx++) {
			//1-2-1. 구간 합을 구한다.
			number[idx] = Integer.parseInt(st.nextToken()) + number[idx - 1];
		}

		//1-3. 구갑 합을 계산할 시작 구간과 끝 구간을 횟수만큼 입력받는다.
		for (int idx = 0; idx < count; idx++) {
			st = new StringTokenizer(input.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			//2. 구간 합의 끝 구간에서 시작 구간까지를 뺀다.
			output.append(number[end] - number[start - 1]).append("\n");
		}
		
		
		System.out.println(output);
	}
}
