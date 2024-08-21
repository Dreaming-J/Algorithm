/*
#BOJ 1074. Z

1. 배열의 크기, 방문하려는 행과 열 입력
2. 분할정복을 통해 2x2 크기가 될 때까지 탐색
	2-0. 타겟을 찾았다면, 탐색을 포기한다.
	2-1. 사이즈가 2라면, z 탐색을 시작한다.
	2-2. 사이즈가 2가 아니라면, z 탐색을 위해 4등분 하여 탐색한다.
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static final int[][] deltas = {{0, 0}, {0, 1}, {1, 0}, {1, 1}}; //z 탐색을 위한 델타 배열
	static int Z_SEARCH_START_SIZE = 2;
	static int mapSize;
	static int targetRow, targetCol;
	static boolean findTarget;
	static int visitCount;
	
	//2. 분할정복을 통해 2x2 크기가 될 때까지 탐색
	public static void dq(int row, int col, int size) {
		//2-0. 타겟을 찾았다면, 탐색을 포기한다.
		if (findTarget)
			return;
		
		//2-1. 사이즈가 2라면, z 탐색을 시작한다.
		if (size == Z_SEARCH_START_SIZE) {
			for (int[] delta : deltas) {
				visitCount++;
				if (row + delta[0] == targetRow && col + delta[1] == targetCol) {
					findTarget = true;
					return;
				}
			}
			return;
		}

		//2-2. 사이즈가 2가 아니라면, z 탐색을 위해 4등분 하여 탐색한다.
		int half = size / 2;
		
		if (targetCol < col + half) {
			//현재 격자 중심 기준으로 제 2 사분면에 위치할 경우
			if (targetRow < row + half) {
				dq(row, col, half);
				return;
			}
			
			//현재 격자 중심 기준으로 제 3 사분면에 위치할 경우
			else {
				visitCount += half * half * 2;
				dq(row + half, col, half);
				return;
			}
		}
		else {
			//현재 격자 중심 기준으로 제 1 사분면에 위치할 경우
			if (targetRow < row + half) {
				visitCount += half * half;
				dq(row, col + half, half);
				return;
			}
			
			//현재 격자 중심 기준으로 제 4 사분면에 위치할 경우
			else {
				visitCount += half * half * 3;
				dq(row + half, col + half, half);
				return;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 배열의 크기, 방문하려는 행과 열 입력
		st = new StringTokenizer(input.readLine().trim());
		mapSize = (int) Math.pow(2, Integer.parseInt(st.nextToken()));
		targetRow = Integer.parseInt(st.nextToken());
		targetCol = Integer.parseInt(st.nextToken());
		
		//2. 분할정복을 통해 2x2 크기가 될 때까지 탐색
		visitCount = -1;
		dq(0, 0, mapSize);
				
		//3. 출력
		System.out.println(visitCount);
	}
}
