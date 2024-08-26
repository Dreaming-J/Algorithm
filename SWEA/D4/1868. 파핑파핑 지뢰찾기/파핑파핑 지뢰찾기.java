/*
= SWEA 1868. 파핑파핑 지뢰찾기

0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 맵의 크기 입력
	1-2. 맵의 크기만큼 맵 정보 입력
	1-3. 변수 초기화
2. 0인 공간 찾기
	2-1. 해당 좌표 방문했는지 판단
	2-2. 해당 좌표의 값이 0이 아니라면 탐색 종료
	2-3. 팔방으로 이동
3. 방문한 적 없는 빈칸 갯수  구하기
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Solution {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int[][] deltas = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
	static final int EMPTY = '.', MINE = '*';
	static int testCase;
	static int mapSize;
	static char[][] map;
	static boolean[][] visitMap;
	static int count;
	
	public static class Position {
		int row;
		int col;
		
		public Position(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static void findZeroPlace(int row, int col) {
		Deque<Position> queue = new ArrayDeque<>();
		queue.add(new Position(row, col));
		
		while (!queue.isEmpty()) {
			Position cur = queue.poll();
			
			//2-1. 해당 좌표 방문했는지 판단
			if (visitMap[cur.row][cur.col]) {
				continue;
			}
			visitMap[cur.row][cur.col] = true;
			
			//2-2. 해당 좌표의 값이 0이 아니라면 탐색 종료
			if (countMine(cur.row, cur.col) != 0) {
				continue;
			}
			
			//2-3. 팔방으로 이동
			for (int[] delta : deltas) {
				int nr = cur.row + delta[0];
				int nc = cur.col + delta[1];
				
				if (!canGo(nr, nc))
					continue;
				
				if (map[nr][nc] == MINE)
					continue;
				
				queue.add(new Position(nr, nc));
			}
		}
	}
	
	public static int countMine(int row, int col) {
		int countMine = 0;
		
		for (int[] delta : deltas) {
			int nr = row + delta[0];
			int nc = col + delta[1];
			
			if (!canGo(nr, nc))
				continue;
			
			countMine += map[nr][nc] == MINE ? 1 : 0;
		}
		
		return countMine;
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < mapSize && col >= 0 && col < mapSize;
	}
	
	public static void main(String[] args) throws IOException {
		//0. 테스트 케이스 입력
		testCase = Integer.parseInt(input.readLine().trim());
		
		for (int tc = 1; tc <= testCase; tc++) {
			//1. 초기 세팅
			initTestCase();
			
			//2. 0인 공간 찾기
			for (int row = 0; row < mapSize; row++) {
				for (int col = 0; col < mapSize; col++) {
					if (!visitMap[row][col] && map[row][col] == EMPTY && countMine(row, col) == 0) {
						count++;
						findZeroPlace(row, col);
					}
				}
			}
			
			//3. 방문한 적 없는 빈칸 갯수  구하기
			for (int row = 0; row < mapSize; row++) {
				for (int col = 0; col < mapSize; col++) {
					if (!visitMap[row][col] && map[row][col] == EMPTY) {
						count++;
						visitMap[row][col] = true;
					}
				}
			}
			
			//4. 출력
			output.append("#").append(tc).append(" ").append(count).append("\n");
		}
		System.out.println(output);
	}
	
	public static void initTestCase() throws IOException {
		//1-1. 맵의 크기 입력
		mapSize = Integer.parseInt(input.readLine().trim());
		
		//1-2. 맵의 크기만큼 맵 정보 입력
		map = new char[mapSize][mapSize];
		for (int row = 0; row < mapSize; row++) {
			map[row] = input.readLine().toCharArray();
		}
		
		//1-3. 변수 초기화
		visitMap = new boolean[mapSize][mapSize];
		count = 0;
	}
}
