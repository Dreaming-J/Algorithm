import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
=백준 11660. 구간 합 구하기 5

1. 배열의 크기와 구간 합을 구해야 하는 횟수를 입력받는다.
2. 배열의 크기보다 1씩 큰 배열로 초기화한다.
3. 배열의 크기만큼 행을 입력받는다.
	3-1. 구간 합을 구한다.
4. 구갑 합 횟수만큼 시작 구간과 종료 구간의 좌표를 입력받는다.
	4-1. 주어진 좌표에 해당하는 구간 합을 계산한다.
 */
public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static int numberSize, intervalSize;
	static int[][] board;
	
	public static void main(String[] args) throws IOException {
		//1. 배열의 크기와 구간 합을 구해야 하는 횟수를 입력받는다.
		st = new StringTokenizer(input.readLine().trim());
		numberSize = Integer.parseInt(st.nextToken());
		intervalSize = Integer.parseInt(st.nextToken());
		
		//2. 배열의 크기보다 1씩 큰 배열로 초기화한다.
		board = new int[numberSize + 1][numberSize + 1];
		
		//3. 배열의 크기만큼 행을 입력받는다.
		for (int row = 1; row <= numberSize; row++) {
			st = new StringTokenizer(input.readLine().trim());
			for (int col = 1; col <= numberSize; col++) {
				//3-1. 구간 합을 구한다.
				board[row][col] = Integer.parseInt(st.nextToken()) +
						board[row - 1][col] +
						board[row][col -1] -
						board[row - 1][col - 1];
			}
		}
		
		//4. 구갑 합 횟수만큼 시작 구간과 종료 구간의 좌표를 입력받는다.
		for (int count = 0; count < intervalSize; count++) {
			//4-1. 주어진 좌표에 해당하는 구간 합을 계산한다.
			st = new StringTokenizer(input.readLine().trim());
			int[] start = {Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1};
			int[] end = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
			int interval = board[end[0]][end[1]] -
					board[start[0]][end[1]] - 
					board[end[0]][start[1]] +
					board[start[0]][start[1]];
			output.append(interval).append("\n");
		}
		System.out.println(output);
	}
}
