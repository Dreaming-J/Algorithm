/*
= SWEA 7733. 치즈 도둑

0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 치즈 한 변의 길이 입력
	1-2. 치즈 한 변의 길이만큼 치즈 정보 입력
	1-3. 변수 초기화
2. 0일부터 100일까지 치즈 덩어리 계산
	2-1. 해당 좌표에 치즈 덩어리가 있는지 판단
		2-1-1. 해당 좌표를 방문한 적이 있으면 패스
		2-1-2. 해당 좌표가 먹어버린 칸이라면 패스
		2-1-3. 해당 좌표 주변 덩어리 탐색
	2-2. 최대 치즈 덩어리 개수 업데이트
	2-3. 치즈 덩어리가 0개라면 반복 종료
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Solution {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int MAX_TIME = 100;
	static final int[][] deltas = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	static int testCase;
	static int cheeseSize;
	static int[][] cheese;
	static boolean[][] visited;
	static int maxBunch;
	
	public static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static void findBunch(int time, int row, int col) {
		Deque<Point> queue = new ArrayDeque<>();
		queue.add(new Point(row, col));
		visited[row][col] = true;
		
		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			
			for (int[] delta : deltas) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];
				
				//범위를 벗어나면 패스
				if (!canGo(nr, nc))
					continue;
				
				//방문했던 칸이면 패스
				if (visited[nr][nc])
					continue;
				
				//먹어버린 칸이면 패스
				if (cheese[nr][nc] <= time)
					continue;
				
				visited[nr][nc] = true;
				queue.add(new Point(nr, nc));
			}
		}
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < cheeseSize && col >= 0 && col < cheeseSize;
	}
	
	public static void main(String[] args) throws IOException {
		//0. 테스트 케이스 입력
		testCase = Integer.parseInt(input.readLine().trim());
		
		for (int tc = 1; tc <= testCase; tc++) {
			//1. 초기 세팅
			initTestCase();
			
			//2. 0일부터 100일까지 치즈 덩어리 계산
			for (int time = 0; time <= MAX_TIME; time++) {
				visited = new boolean[cheeseSize][cheeseSize];
				
				//2-1. 해당 좌표에 치즈 덩어리가 있는지 판단
				int bunch = 0;
				for (int row = 0; row < cheeseSize; row++) {
					for (int col = 0; col < cheeseSize; col++) {
						//2-1-1. 해당 좌표를 방문한 적이 있으면 패스
						if (visited[row][col])
							continue;
						
						//2-1-2. 해당 좌표가 먹어버린 칸이라면 패스
						if (cheese[row][col] <= time)
							continue;
						
						//2-1-3. 해당 좌표 주변 덩어리 탐색
						findBunch(time, row, col);
						bunch++;
					}
				}
				
				//2-2. 최대 치즈 덩어리 개수 업데이트
				maxBunch = Integer.max(bunch, maxBunch);
				
				//2-3. 치즈 덩어리가 0개라면 반복 종료
				if (bunch == 0)
					break;
			}
			
			//4. 출력
			output.append("#").append(tc).append(" ").append(maxBunch).append("\n");
		}
		System.out.println(output);
	}
	
	public static void initTestCase() throws IOException {
		//1-1. 치즈 한 변의 길이 입력
		cheeseSize = Integer.parseInt(input.readLine().trim());
		
		//1-2. 치즈 한 변의 길이만큼 치즈 정보 입력
		cheese = new int[cheeseSize][cheeseSize];
		for (int row = 0; row < cheeseSize; row++) {
			st = new StringTokenizer(input.readLine().trim());
			for (int col = 0; col < cheeseSize; col++) {
				cheese[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		//1-3. 변수 초기화
		maxBunch = 0;
	}
}