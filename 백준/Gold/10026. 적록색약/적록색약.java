/*
= BOJ 10026. 적록색약

=로직
1. 초기 세팅
	1-1. 그림의 크기 입력
	1-2. 그림의 크기만큼 그림의 RGB 정보 입력
	1-3. 변수 초기화
2. 그림의 구역 탐색
	2-1. 적록색맹과 정산인의 구역 탐색
		2-1-1. 방문한 적 있으면 패스
		2-1-2. 적록색맹은 파란색 영역은 탐색하지 않음
		2-1-3. 방문한 적 없다면 구역 탐색 -> 3번으로 이동
3. 해당 구역 탐색
	3-1. 방문 처리
	3-2. 다음 칸으로 이동
		3-2-1. 범위를 벗어났다면 패스
		3-2-2. 방문했던 칸이라면 패스
		3-2-3. 정상인이고 서로 다른 색이라면 패스
		3-2-4. 적록색약이고 파란색이라면 패스
		3-2-5. 다음 칸으로 이동
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
	static StringBuilder output = new StringBuilder();
	static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static final int NORMAL = 0, WEAKNESS = 1;
	static final char BLUE = 'B';
	static int paintingSize;
	static char[][] painting;
	static boolean[][][] visited;
	static int[] sectionCount;
	
	public static void findSection(int isWeakness, int row, int col, char color) {
		//3-1. 방문 처리
		visited[isWeakness][row][col] = true;
		
		//3-2. 다음 칸으로 이동
		for (int[] delta : deltas) {
			int nr = row + delta[0];
			int nc = col + delta[1];
			
			//3-2-1. 범위를 벗어났다면 패스
			if (!canGo(nr, nc))
				continue;
			
			//3-2-2. 방문했던 칸이라면 패스
			if (visited[isWeakness][nr][nc])
				continue;

			//3-2-3. 정상인이고 서로 다른 색이라면 패스
			if (isWeakness == NORMAL && painting[nr][nc] != color)
				continue;

			//3-2-4. 적록색약이고 파란색이라면 패스
			if (isWeakness == WEAKNESS && painting[nr][nc] == BLUE) {
				continue;
			}
			
			//3-2-5. 다음 칸으로 이동
			findSection(isWeakness, nr, nc, color);
		}
	}
	
	public static boolean canGo(int row, int col) {
		return row >= 0 && row < paintingSize && col >= 0 && col < paintingSize;
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 그림의 구역 탐색
		for (int row = 0; row < paintingSize; row++) {
			for (int col = 0; col < paintingSize; col++) {
				//2-1. 적록색맹과 정산인의 구역 탐색
				for (int idx = 0; idx < 2; idx++) {
					//2-1-1. 방문한 적 있으면 패스
					if (visited[idx][row][col])
						continue;
					
					//2-1-2. 적록색맹은 파란색 영역은 탐색하지 않음
					if (painting[row][col] == BLUE) {
						if (idx == NORMAL)
							sectionCount[WEAKNESS]++;
						if (idx == WEAKNESS)
							continue;
					}
					
					//2-1-3. 방문한 적 없다면 구역 탐색 -> 3번으로 이동
					findSection(idx, row, col, painting[row][col]);
					sectionCount[idx]++;
				}
			}
		}
		
		//4. 출력
		System.out.println(sectionCount[NORMAL] + " " + sectionCount[WEAKNESS]);
	}
	
	public static void init() throws IOException {
		//1-1. 그림의 크기 입력
		paintingSize = Integer.parseInt(input.readLine());
		
		//1-2. 그림의 크기만큼 그림의 RGB 정보 입력
		painting = new char[paintingSize][paintingSize];
		for (int row = 0; row < paintingSize; row++) {
			painting[row] = input.readLine().toCharArray();
		}
		
		//1-3. 변수 초기화
		visited = new boolean[2][paintingSize][paintingSize];
		sectionCount = new int[2];
	}
}
