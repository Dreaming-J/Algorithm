/*
#BOJ 3109. 빵집

1. 초기화
	1-1. 행, 열 입력
	1-2. 행의 크기만큼 빵집의 정보 입력
2. 파이프별로 가장 최적의 경로로 설치를 시도한다. (최대한 위로 설치하는 것이 최적의 경로)
	2-1. 선택된 파이프라인이 설치됐다면 탐색 종료
	2-2. 마지막 열에 도착했다면 탐색 종료
	2-3. 다음으로 이동
		2-3-1. 범위를 벗어났다면 패스
		2-3-2. 이동할 장소에 빌딩이나 파이프가 놓여있다면 패스
		2-3-3. 이 파이프라인이 설치됐다면 탐색 종료
		2-3-4. 이동
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static final int[][] deltas = {{-1, 1}, {0, 1}, {1, 1}};
	static final char EMPTY = '.', BUILDING = 'x', PIPE = '-';
	static int rowSize, colSize;
	static char[][] bakery;
	static boolean finished;
	static int maxPipelineCount;
	
	public static void findMaxPipeline(int row, int col) {
		//2-2. 마지막 열에 도착했다면 탐색 종료
		if (col == colSize - 1) {
			finished = true;
			maxPipelineCount++;
			return;
		}
		
		//2-3. 다음으로 이동
		for (int[] delta : deltas) {
			int nr = row + delta[0];
			int nc = col + delta[1];
			
			//2-3-1. 범위를 벗어났다면 패스
			if (nr < 0 || nr >= rowSize || nc < 0 || nc >= colSize)
				continue;
			
			//2-3-2. 이동할 장소에 빌딩이나 파이프가 놓여있다면 패스
			if (bakery[nr][nc] == BUILDING || bakery[nr][nc] == PIPE)
				continue;
			
			//2-3-3. 이 파이프라인이 설치됐다면 탐색 종료
			if (finished)
				return;
			
			//2-3-4. 이동
			bakery[nr][nc] = PIPE;
			findMaxPipeline(nr, nc);
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기화
		init();
		
		//2. 파이프별로 가장 최적의 경로로 설치를 시도한다. (최대한 위로 설치하는 것이 최적의 경로)
		for (int pipeIdx = 0; pipeIdx < rowSize; pipeIdx++) {
			finished = false;
			findMaxPipeline(pipeIdx, 0);
		}
		
		//3. 출력
		System.out.println(maxPipelineCount);
	}
	
	public static void init() throws IOException {
		//1-1. 행, 열 입력
		st = new StringTokenizer(input.readLine().trim());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		
		//1-2. 행의 크기만큼 빵집의 정보 입력
		bakery = new char[rowSize][colSize];
		for (int row = 0; row < rowSize; row++)
			bakery[row] = input.readLine().toCharArray();
	}
}
