/*
= BOJ 2178. 미로 탐색

=특이 사항
도착 위치로 이동할 수 있는 경우 보장

= 로직
1. 초기 세팅
	1-1. 미로의 행과 열 크기 입력
	1-2. 행 크기만큼 미로 정보 입력
	1-3. 변수 초기화
2. BFS를 이용하여 도착 위치까지 이동
	2-1. 도착 위치에 도착했다면 
3. 출력
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
	static final int[][] deltas = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
	static final char WALL = '0';
	static int rowSize, colSize;
	static char[][] maze;
	static boolean[][] visited;
	static int minTime;
	
	public static class Point {
		int row;
		int col;
		int time;
		
		public Point(int row, int col, int time) {
			this.row = row;
			this.col = col;
			this.time = time;
		}
		
		public boolean isGoal() {
			return row == rowSize - 1 && col == colSize - 1;
		}
	}
	
	public static void findGoal() {
		Deque<Point> queue = new ArrayDeque<>();
		queue.add(new Point(0, 0, 1));
		visited[0][0] = true;
		
		while (!queue.isEmpty()) {
			Point cur = queue.poll();
			
			if (cur.isGoal()) {
				minTime = cur.time;
				break;
			}
			
			for (int[] delta : deltas) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];
				
				if (!canGo(nr, nc))
					continue;
				
				if (visited[nr][nc])
					continue;
				
				if (maze[nr][nc] == WALL)
					continue;
				
				visited[nr][nc] = true;
				queue.add(new Point(nr, nc, cur.time + 1));
			}
		}
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		initTestCase();

		//2. BFS를 이용하여 도착 위치까지 이동
		findGoal();
		
		//3. 출력
		System.out.println(minTime);
	}
	
	public static void initTestCase() throws IOException {
		//1-1. 미로의 행과 열 크기 입력
		st = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		
		//1-2. 행 크기만큼 미로 정보 입력
		maze = new char[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			maze[row] = input.readLine().toCharArray();
		}
		
		//1-3. 변수 초기화
		visited = new boolean[rowSize][colSize];
	}
}
