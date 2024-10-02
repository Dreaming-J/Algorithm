/*
= BOJ 1486. 등산
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static final int INF = 100_000;
	static int rowSize, colSize, gapSize, time;
	static int[][] mountain;
	static int pointSize;
	static int[][] adjMatrix;
	static int maxHeight;
	
	public static void makeAdjMatrix() {
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				for (int[] delta : DELTA) {
					int nr = row + delta[0];
					int nc = col + delta[1];
					if (isOut(nr, nc))
						continue;
					
					int from = row * colSize + col;
					int to = nr * colSize + nc;
					
					int gap = mountain[row][col] - mountain[nr][nc];
					
					if (Math.abs(gap) <= gapSize)
						adjMatrix[from][to] = gap < 0 ? gap * gap : 1;
				}
			}
		}
	}
	
	public static boolean isOut(int row, int col) {
		return row < 0 || row >= rowSize || col < 0 || col >= colSize;
	}
	
	public static void findMinTime() {
		for (int layOver = 0; layOver < pointSize; layOver++) {
			for (int start = 0; start < pointSize; start++) {
				for (int end = 0; end < pointSize; end++) {
					
					adjMatrix[start][end] = Math.min(adjMatrix[start][layOver] + adjMatrix[layOver][end], adjMatrix[start][end]);
				}
			}
		}
	}
	
	public static void findMaxHeight() {
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {
				int to = row * colSize + col;
				int duration = adjMatrix[0][to] + adjMatrix[to][0];
				
				if (duration <= time) {
					maxHeight = Math.max(mountain[row][col], maxHeight);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		//초기 세팅
		init();
		
		//인접행렬 값 채우기
		makeAdjMatrix();
		
		//각 위치별 최단 이동 시간 찾기
		findMinTime();
		
		//갈 수 있는 최대 높이 찾기
		findMaxHeight();
		
		//출력
		System.out.println(maxHeight);
	}
	
	//초기 세팅
	public static void init() throws IOException {
		//세로, 가로, 이동 가능 높이 차이, 시간 입력
		st = new StringTokenizer(input.readLine());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		gapSize = Integer.parseInt(st.nextToken());
		time = Integer.parseInt(st.nextToken());
		
		//산의 정보 입력
		mountain = new int[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			String line = input.readLine();
			for (int col = 0; col < colSize; col++) {
				char c = line.charAt(col);
				mountain[row][col] = c >= 'a' ? c - 'a' + 26 : c - 'A';
			}
		}
		
		//인접행렬 생성
		pointSize = rowSize * colSize;
		adjMatrix = new int[pointSize][pointSize];
		for (int row = 0; row < pointSize; row++) {
			for (int col = 0; col < pointSize; col++) {
				if (row == col)
					continue;
				
				adjMatrix[row][col] = INF;
			}
		}
	}
}
