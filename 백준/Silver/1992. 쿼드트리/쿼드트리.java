/*
#BOJ 1992. 쿼드트리

1. 영상의 크기 입력
2. 영상의 크기만큼 영상 정보 입력
3. 분할 정복을 이용해 쿼드 트리 진행
	3-1. 주어진 공간이 모두 같은 값인지 체크
	3-2. 모두 흰 점인 경우, 0 추가 후 종료
	3-3. 모두 검은 점인 경우, 1 추가 후 종료
	3-4. 모두 같은 값이 아니라면 4분할
		3-4-1. 열린 괄호 추가
		3-4-2. 4분할 진행
		3-4-3. 닫힌 괄호 추가
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static final int WHITE = 0, BLACK = 1;
	static final char OPEN = '(', CLOSED = ')';
	static int pictureSize;
	static int[][] picture;
	static StringBuilder result;
	
	public static void quadTree(int row, int col, int size) {
		//3-1. 주어진 공간이 모두 같은 값인지 체크
		int sum = 0;
		for (int r = row, rEnd = row + size; r < rEnd; r++) {
			for (int c = col, cEnd = col + size; c < cEnd; c++)
				sum += picture[r][c];
		}
		
		//3-2. 모두 흰 점인 경우, 0 추가 후 종료
		if (sum == 0) {
			result.append(WHITE);
			return;
		}
		
		//3-3. 모두 검은 점인 경우, 1 추가 후 종료
		if (sum == size * size) {
			result.append(BLACK);
			return;
		}
		
		//3-4. 모두 같은 값이 아니라면 4분할
		int half = size / 2;
		//3-4-1. 열린 괄호 추가
		result.append(OPEN);
		
		//3-4-2. 4분할 진행
		quadTree(row, col, half);
		quadTree(row, col + half, half);
		quadTree(row + half, col, half);
		quadTree(row + half, col + half, half);
		
		//3-4-3. 닫힌 괄호 추가
		result.append(CLOSED);
	}
	
	public static void main(String[] args) throws IOException {
		//1. 영상의 크기 입력
		pictureSize = Integer.parseInt(input.readLine().trim());
		
		//2. 영상의 크기만큼 영상 정보 입력
		picture = new int[pictureSize][pictureSize];
		for (int row = 0; row < pictureSize; row++) {
			String line = input.readLine();
			for (int col = 0; col < pictureSize; col++)
				picture[row][col] = line.charAt(col) - '0';
		}
		
		//3. 분할 정복을 이용해 쿼드 트리 진행
		result = new StringBuilder();
		quadTree(0, 0, pictureSize);
				
		//4. 출력
		System.out.println(result);
	}
}
