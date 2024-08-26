/*
= BOJ 2636. 치즈

1. 초기 세팅
	1-1. 행과 열의 크기 입력
	1-2. 행의 크기만큼 보드 정보 입력
		1-2-1. 총 치즈 개수 계산
2. 치즈가 모두 사라질 때까지 멜팅 시작
	2-1. 경과 시간 증가
	2-2. bfs를 이용하여 겉에 있는 모든 치즈 탐색 후 녹아 없어짐
		2-2-1. (0, 0)부터 배열 탐색
		2-2-2. 현재 위치가 치즈라면 탐색 종료
		2-2-3. 다음 위치로 이동
	2-3. 사라진 치즈만큼 치즈 개수 감소
3, 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int[][] deltas = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	static final int AIR = 0, CHEESE = 1;
	static int rowSize, colSize;
	static int[][] cheese;
	static boolean[][] visited;
	static int cheeseCount, elapsedTime, lastCheese;
	
	public static class Position {
		int row;
		int col;
		
		public Position(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static void findExteriorCheese() {
		lastCheese = 0;

		//2-2-1. (0, 0)부터 배열 탐색
		Deque<Position> queue = new ArrayDeque<>();
		queue.add(new Position(0, 0));
		visited[0][0] = true;
		
		while (!queue.isEmpty()) {
			Position cur = queue.poll();
			
			//2-2-2. 현재 위치가 치즈라면 탐색 종료
			if (cheese[cur.row][cur.col] == CHEESE) {
				cheese[cur.row][cur.col] = AIR;
				lastCheese++;
				continue;
			}
			
			//2-2-3. 다음 위치로 이동
			for (int[] delta : deltas) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];
				
				//범위를 벗어나면 패스
				if (!canGo(nr, nc))
					continue;
				
				//방문했던 칸이면 패스
				if (visited[nr][nc])
					continue;
				
				visited[nr][nc] = true;
				queue.add(new Position(nr, nc));
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 치즈 멜팅 시작
		while (cheeseCount > 0) {
			//2-1. 경과 시간 증가
			elapsedTime++;
			
			//2-2. bfs를 이용하여 겉에 있는 모든 치즈 탐색 후 녹아 없어짐
			visited = new boolean[rowSize][colSize];
			findExteriorCheese();
			
			//2-3. 사라진 치즈만큼 치즈 개수 감소
			cheeseCount -= lastCheese;
		}
		
		//3. 출력
		output.append(elapsedTime).append(" ").append(lastCheese);
		System.out.println(output);
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
	}
	
	public static void init() throws IOException {
		//1-1. 행과 열의 크기 입력
		st = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		
		//1-2. 행의 크기만큼 보드 정보 입력
		cheese = new int[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			st = new StringTokenizer(input.readLine());
			for (int col = 0; col < colSize; col++) {
				cheese[row][col] = Integer.parseInt(st.nextToken());
				
				//1-2-1. 총 치즈 개수 계산
				if (cheese[row][col] == CHEESE)
					cheeseCount++;
			}
		}
	}
}
