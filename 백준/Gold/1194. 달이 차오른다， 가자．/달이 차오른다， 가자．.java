/*
= BOJ 1194. 달이 차오른다, 가자.

= 로직
1. 초기 세팅
	1-1. 미로의 세로, 가로 크기 입력
	1-2. 미로의 정보 입력
		1-2-1. 시작 위치 저장
2. 미로 탈출
	2-1. 변수 초기화
	2-2. 시작 위치 설정
	2-3. 미로 이동
	2-4. 탈출하지 못한 경우
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
	static StringTokenizer st;
	static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static final char EMPTY = '.', WALL = '#', START = '0', EXIT = '1';
	static final char KEY_START = 'a', KEY_END = 'f', DOOR_START = 'A', DOOR_END = 'F';
	static final int KEY_SIZE = 6;
	static int rowSize, colSize;
	static char[][] maze;
	static Point start;
	static int moveCount;
	
	//2. 미로 탈출
	public static void escapeMaze() {
		//2-1. 변수 초기화
		boolean[][][] visited = new boolean[rowSize][colSize][1 << KEY_SIZE];
		Deque<Point> queue = new ArrayDeque<>();
		
		//2-2. 시작 위치 설정
		visited[start.row][start.col][0] = true;
		queue.add(start);
		
		//2-3. 미로 이동
		while (!queue.isEmpty()) {
			int size = queue.size();
			
			while (size-- > 0) {
				Point cur = queue.poll();
				
				//출구라면 종료
				if (maze[cur.row][cur.col] == EXIT)
					return;
				
				//열쇠라면 줍기
				if (isKey(cur.row, cur.col))
					cur.hasKeyBit |= 1 << (maze[cur.row][cur.col] - KEY_START);
				
				//다음 칸으로 이동
				for (int[] delta : DELTA) {
					int nr = cur.row + delta[0];
					int nc = cur.col + delta[1];
					
					//범위를 벗어났다면
					if (isOut(nr, nc))
						continue;
					
					//방문했다면
					if (visited[nr][nc][cur.hasKeyBit])
						continue;
					
					//벽이라면
					if (maze[nr][nc] == WALL)
						continue;
					
					//문이라면
					if (isDoor(nr, nc)) {
						//해당 문의 열쇠를 가지고 있다면
						if ((cur.hasKeyBit & 1 << (maze[nr][nc] - DOOR_START)) != 0) {
							visited[nr][nc][cur.hasKeyBit] = true;
							queue.add(new Point(nr, nc, cur.hasKeyBit));
						}
						continue;
					}
					
					//빈 칸, 열쇠, 출구라면
					visited[nr][nc][cur.hasKeyBit] = true;
					queue.add(new Point(nr, nc, cur.hasKeyBit));
				}
			}
			moveCount++;
		}
		
		//2-4. 탈출하지 못한 경우
		moveCount = -1;
	}
	
	public static boolean isKey(int row, int col) {
		return maze[row][col] >= KEY_START && maze[row][col] <= KEY_END;
	}
	
	public static boolean isDoor(int row, int col) {
		return maze[row][col] >= DOOR_START && maze[row][col] <= DOOR_END;
	}
	
	public static boolean isOut(int row, int col) {
		return row < 0 || row >= rowSize || col < 0 || col >= colSize;
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 미로 탈출
		escapeMaze();
		
		//3. 출력
		System.out.println(moveCount);
	}
	
	public static class Point {
		int row, col, hasKeyBit;

		public Point(int row, int col, int hasKeyBit) {
			this.row = row;
			this.col = col;
			this.hasKeyBit = hasKeyBit;
		}
	}
	
	//1. 초기 세팅
	public static void init() throws IOException {
		//1-1. 미로의 세로, 가로 크기 입력
		st = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		
		//1-2. 미로의 정보 입력
		maze = new char[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			String line = input.readLine();
			for (int col = 0; col < colSize; col++) {
				maze[row][col] = line.charAt(col);
				
				//1-2-1. 시작 위치 저장
				if (maze[row][col] == START) {
					start = new Point(row, col, 0);
					maze[row][col] = EMPTY;
				}
			}
		}
	}
}
