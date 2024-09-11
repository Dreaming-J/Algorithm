/*
= BOJ 21610. 마법사 상어와 비바라기

= 로직
1. 초기 세팅
	1-1. 보드의 크기, 이동 횟수 입력
	1-2. 보드 정보 입력
	1-3. 변수 초기화
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
	static int[][] DELTA = {{0, 0}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};
	static int mapSize, moveSize;
	static int[][] map;
	static Deque<Point> clouds, waterCopy;
	static boolean[][] visited;
	static int water;
	
	public static void move(int direction, int spatium) {
		while (!clouds.isEmpty()) {
			Point cur = clouds.poll();
			int row = cur.row + DELTA[direction][0] * spatium;
			int col = cur.col + DELTA[direction][1] * spatium;
			
			int nr = (mapSize * 100 + row) % mapSize;
			int nc = (mapSize * 100 + col) % mapSize;

			visited[nr][nc] = true;
			map[nr][nc]++;
			
			waterCopy.add(new Point(nr, nc));
		}
	}
	
	public static void waterCopy() {
		while (!waterCopy.isEmpty()) {
			Point cur = waterCopy.poll();
			
			for (int idx = 1; idx < DELTA.length; idx++) {
				if (idx % 2 != 0)
					continue;
				
				int nr = cur.row + DELTA[idx][0];
				int nc = cur.col + DELTA[idx][1];
				
				if (!canGo(nr, nc))
					continue;
				
				if (map[nr][nc] == 0)
					continue;
				
				map[cur.row][cur.col]++;
			}
		}
	}
	
	public static void makeCloud() {
		for (int row = 0; row < mapSize; row++) {
			for (int col = 0; col < mapSize; col++) {
				if (visited[row][col])
					continue;
				
				if (map[row][col] < 2)
					continue;
				
				map[row][col] -= 2;
				clouds.add(new Point(row, col));
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 이동 정보 입력 후 이동
		for (int size = 0; size < moveSize; size++) {
			st = new StringTokenizer(input.readLine());
			
			visited = new boolean[mapSize][mapSize];
			
			move(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

			waterCopy();
			
			makeCloud();
		}
		
		//3. 출력
		for (int row = 0; row < mapSize; row++) {
			for (int col = 0; col < mapSize; col++) {
				water += map[row][col];
			}
		}
		System.out.println(water);
	}
	
	public static class Point {
		int row, col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < mapSize && col >= 0 && col < mapSize;
	}
	
	public static void init() throws IOException {
		//1-1. 보드의 크기, 이동 횟수 입력
		st = new StringTokenizer(input.readLine());
		mapSize = Integer.parseInt(st.nextToken());
		moveSize = Integer.parseInt(st.nextToken());
		
		//1-2. 보드 정보 입력
		map = new int[mapSize][mapSize];
		for (int row = 0; row < mapSize; row++) {
			st = new StringTokenizer(input.readLine());
			for (int col = 0; col < mapSize; col++) {
				map[row][col] = Integer.parseInt(st.nextToken());
			}
		}
		
		//1-3. 변수 초기화
		clouds = new ArrayDeque<>();
		clouds.add(new Point(mapSize - 1, 0));
		clouds.add(new Point(mapSize - 1, 1));
		clouds.add(new Point(mapSize - 2, 0));
		clouds.add(new Point(mapSize - 2, 1));
		waterCopy = new ArrayDeque<>();
	}
}
