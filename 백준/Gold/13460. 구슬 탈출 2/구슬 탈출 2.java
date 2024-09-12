/*
= BOJ 13460. 구슬 탈출 2

= 특이 사항
빨간 구슬이 구멍에 빠지면 성공, 파란 구슬은 빠지면 실패
동시에 빠져도 실패

= 로직
1. 초기 세팅
	1-1. 보드의 크기 입력
	1-2. 보드 정보 입력
		1-2-1. 구슬 좌표 획득
	1-3. 변수 초기화
2. 최소 이동 횟수 탐색
	2-1. 10번 이하의 회수로만 구슬 이동
	2-2. 파랑 구슬이 구멍에 도착했다면 패스
	2-3. 빨간 구슬이 구멍에 도착했다면 탐색 종료
	2-4. 다음으로 이동
		2-4-1. 다음 위치 찾기
		2-4-2. 이동할 위치가 구멍이 아닌데 두 구슬이 같은 좌표에 있다면 한 칸 뒤로 이동
		2-4-3. 이동하지 않았다면 패스
3. 빨간 구슬을 빼내지 못했다면 정답 -1로 변경
4. 출력
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
	static final int[][] DELTA = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
	static final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3, MAX_MOVE_COUNT = 10;
	static final char EMPTY = '.', WALL = '#', HOLE = 'O', RED = 'R', BLUE = 'B';
	static int rowSize, colSize;
	static char[][] board;
	static Beed beed;
	static int minMoveCnt;
	
	public static void findMinMoveCnt() {
		Deque<Beed> queue = new ArrayDeque<>();
		queue.add(beed);
		
		//2-1. 10번 이하의 회수로만 구슬 이동
		int cnt = 0;
		while (cnt <= MAX_MOVE_COUNT) {
			int size = queue.size();
			
			while (size-- > 0) {
				Beed cur = queue.poll();
				Point red = cur.red;
				Point blue = cur.blue;
				
				//2-2. 파랑 구슬이 구멍에 도착했다면 패스
				if (board[blue.row][blue.col] == HOLE)
					continue;
				
				//2-3. 빨간 구슬이 구멍에 도착했다면 탐색 종료
				if (board[red.row][red.col] == HOLE) {
					minMoveCnt = cnt;
					return;
				}
				
				//2-4. 다음으로 이동
				for (int direction = 0; direction < DELTA.length; direction++) {
					if (direction % 2 == cur.direction % 2)
						continue;
					
					//2-4-1. 다음 위치 찾기
					Point next1 = next(cur.getPoint(0, direction), direction);
					Point next2 = next(cur.getPoint(1, direction), direction);
					
					//2-4-2. 이동할 위치가 구멍이 아닌데 두 구슬이 같은 좌표에 있다면 한 칸 뒤로 이동
					if (board[next1.row][next1.col] != HOLE && Point.equal(next1, next2)) {
						next2.row -= DELTA[direction][0];
						next2.col -= DELTA[direction][1];
					}
					
					//2-4-3. 이동하지 않았다면 패스
					Beed next = new Beed(next1, next2, direction);
					if (Point.equal(red, next.red) && Point.equal(blue, next.blue)) {
						continue;
					}
					
					queue.add(next);
				}
			}
			cnt++;
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 최소 이동 횟수 탐색
		findMinMoveCnt();
		
		//3. 빨간 구슬을 빼내지 못했다면 정답 -1로 변경
		if (minMoveCnt == 0)
			minMoveCnt = -1;
		
		//4. 출력
		System.out.println(minMoveCnt);
	}
	
	public static Point next(Point cur, int direction) {
		int nr = cur.row;
		int nc = cur.col;
		
		while (canGo(nr + DELTA[direction][0], nc + DELTA[direction][1]) && board[nr + DELTA[direction][0]][nc + DELTA[direction][1]] != WALL) {
			if (board[nr][nc] == HOLE)
				break;
			
			nr += DELTA[direction][0];
			nc += DELTA[direction][1];
		}
		
		return new Point(nr, nc, cur.color);
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
	}
	
	public static class Beed {
		Point red, blue;
		int direction;

		public Beed(Point p1, Point p2, int direction) {
			this.red = p1.color == RED ? p1 : p2;
			this.blue = p1.color == BLUE ? p1 : p2;
			this.direction = direction;
		}
		
		public Point getPoint(int idx, int direction) {
			if (idx == 0)
				return Point.compare(red, blue, direction) < 0 ? red : blue;
			
			return Point.compare(red, blue, direction) < 0 ? blue : red;
		}
	}
	
	public static class Point {
		int row, col;
		char color;

		public Point(int row, int col, char color) {
			this.row = row;
			this.col = col;
			this.color = color;
		}
		
		public static boolean equal(Point p1, Point p2) {
			return p1.row == p2.row && p1.col == p2.col;
		}
		
		public static int compare(Point red, Point blue, int direction) {
			switch (direction) {
				case LEFT:
					return Integer.compare(red.col, blue.col);
				case RIGHT:
					return Integer.compare(blue.col, red.col);
				case UP:
					return Integer.compare(red.row, blue.row);
				default:
					return Integer.compare(blue.row, red.row);
			}
		}
		
		
	}
	
	public static void init() throws IOException {
		//1-1. 보드의 크기 입력
		st = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		
		//1-2. 보드 정보 입력
		board = new char[rowSize][colSize];
		Point red = null, blue = null;
		for (int row = 0; row < rowSize; row++) {
			String line = input.readLine();
			for (int col = 0; col < colSize; col++) {
				board[row][col] = line.charAt(col);
				
				//1-2-1. 구슬 좌표 획득
				if (board[row][col] == RED) {
					red = new Point(row, col, board[row][col]);
					board[row][col] = EMPTY;
				}
				
				if (board[row][col] == BLUE) {
					blue = new Point(row, col, board[row][col]);
					board[row][col] = EMPTY;
				}
			}
		}
		
		//1-3. 변수 초기화
		beed = new Beed(red, blue, -1);
	}
}
