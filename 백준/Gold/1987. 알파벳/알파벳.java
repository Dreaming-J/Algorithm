/*
= BOJ 1987. 알파벳

1. 초기 세팅
	1-1. 행과 열의 크기 입력
	1-2. 행의 크기만큼 보드 정보 입력
	1-3. 변수 초기화
2. 최대 거리 탐색
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int[][] deltas = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	static final int MAX_DISTANCE = 26;
	static int rowSize, colSize;
	static char[][] board;
	static boolean[][] visited;
	static int maxDistance;
	
	public static void findMaxDistance(int row, int col, int distance, int usedAlphaBit) {
		//모든 알파벳을 돌았다면 더 이상 탐색하지 않음
		if (maxDistance == MAX_DISTANCE)
			return;
		
        //최대 거리 업데이트
		maxDistance = Math.max(distance, maxDistance);
		
		for (int[] delta : deltas) {
			int nr = row + delta[0];
			int nc = col + delta[1];
			
			//보드 범위를 벗어났다면 패스
			if (!canGo(nr, nc))
				continue;
			
			//이미 지나온 보드라면 패스
			if (visited[nr][nc])
				continue;
			
			//이미 밟은적 있는 알파벳이라면 패스
			int alpha = board[nr][nc] - 'A';
			if ((usedAlphaBit & 1 << alpha) != 0)
				continue;
			
			visited[nr][nc] = true;
			findMaxDistance(nr, nc, distance + 1, usedAlphaBit | 1 << alpha);
			visited[nr][nc] = false;
		}
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 최대 거리 탐색
		visited[0][0] = true;
		findMaxDistance(0, 0, 1, 1 << (board[0][0] - 'A'));
		
		System.out.println(maxDistance);
	}
	
	public static void init() throws IOException {
		//1-1. 행과 열의 크기 입력
		st = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		
		//1-2. 행의 크기만큼 보드 정보 입력
		board = new char[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			String line = input.readLine();
			board[row] = line.toCharArray();
		}
		
		//1-3. 변수 초기화
		visited = new boolean[rowSize][colSize];
	}
}
