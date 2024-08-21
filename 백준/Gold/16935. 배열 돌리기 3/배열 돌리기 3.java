/*
#BOJ 16935. 배열 돌리기 3

1. 배열의 세로, 가로 크기 그리고 연산의 수 입력
2. 배열의 세로 크기만큼 배열의 원소 입력
3. 연산의 수만큼 연산 입력
4. 연산 순서에 특정 연산 수행
	4-1. 상하 반전
	4-2. 좌우 반전
	4-3. 오른쪽 90도 회전
	4-4. 왼쪽 90도 회전
	4-5. 그룹을 만든 후, 그룹별로 오른쪽 90도 회전
	4-6. 그룹을 만든 후, 그룹별로 왼쪽 90도 회전
5. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static int rowSize, colSize, operationSize;
	static int[][] map;
	static int[] operations;
	
	//4. 연산 순서에 특정 연산 수행
	public static enum Operation {
		NONE(() -> {}),
		HORIZONTAL(() -> horizontal()),
		VERTICAL(() -> vertical()),
		CLOCKWISE(() -> clockwise()),
		COUNTER_CLOCKWISE(() -> counterClockwise()),
		GROUP_CLOCKWISE(() -> groupClockwise()),
		GROUP_COUNTER_CLOCKWISE(() -> groupCounterClockwise());
		
		final Runnable operation;
		
		Operation(Runnable operation) {
			this.operation = operation;
		}
    	
    	public static void of(int command) {
    		for (Operation dir : values()) {
    			if (dir.ordinal() == command)
    				dir.operation.run();
    		}
    	}
	}
	
	//4-1. 상하 반전
	public static void horizontal() {
		int[][] newArray = new int[rowSize][colSize];
		
		for (int row = 0; row < newArray.length; row++) {
			for (int col = 0; col < newArray[0].length; col++)
				newArray[row][col] = map[newArray.length - row - 1][col];
		}
		
		map = newArray;
	}
	
	//4-2. 좌우 반전
	public static void vertical() {
		int[][] newArray = new int[rowSize][colSize];
		
		for (int row = 0; row < newArray.length; row++) {
			for (int col = 0; col < newArray[0].length; col++)
				newArray[row][col] = map[row][newArray[0].length - col - 1];
		}
		
		map = newArray;
	}

	//4-3. 오른쪽 90도 회전
	public static void clockwise() {
		int[][] newArray = new int[colSize][rowSize];
		
		//가로 <-> 세로 바꾸기
		rowSize += colSize;
		colSize = rowSize - colSize;
		rowSize -= colSize;
		
		for (int row = 0; row < newArray.length; row++) {
			for (int col = 0; col < newArray[0].length; col++)
				newArray[row][col] = map[newArray[0].length - col - 1][row];
		}
		
		map = newArray;
	}

	//4-4. 왼쪽 90도 회전
	public static void counterClockwise() {
		int[][] newArray = new int[colSize][rowSize];
		
		//가로 <-> 세로 바꾸기
		rowSize += colSize;
		colSize = rowSize - colSize;
		rowSize -= colSize;
		
		for (int row = 0; row < newArray.length; row++) {
			for (int col = 0; col < newArray[0].length; col++)
				newArray[row][col] = map[col][newArray.length - row - 1];
		}
		
		map = newArray;
	}

	//4-5. 그룹을 만든 후, 그룹별로 오른쪽 90도 회전
	public static void groupClockwise() {
		int[][] newArray = new int[rowSize][colSize];

		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 2; col++) {
				int[][] group = grouping(1 - col, row);
				
				for (int r = 0; r < rowSize / 2; r++) {
					for (int c = 0; c < colSize / 2; c++) {
						newArray[row * (rowSize / 2) + r][col * (colSize / 2) + c] = group[r][c]; 
					}
				}
			}
		}
		
		map = newArray;
	}

	//4-6. 그룹을 만든 후, 그룹별로 왼쪽 90도 회전
	public static void groupCounterClockwise() {
		int[][] newArray = new int[rowSize][colSize];

		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 2; col++) {
				int[][] group = grouping(col, 1 - row);
				
				for (int r = 0; r < rowSize / 2; r++) {
					for (int c = 0; c < colSize / 2; c++) {
						newArray[row * (rowSize / 2) + r][col * (colSize / 2) + c] = group[r][c]; 
					}
				}
			}
		}
		
		map = newArray;
	}
	
	public static int[][] grouping(int row, int col) {
		row *= (rowSize / 2);
		col *= (colSize / 2);
		
		int[][] group = new int[rowSize / 2][colSize / 2];
		
		for (int r = 0; r < rowSize / 2; r++) {
			for (int c = 0; c < colSize / 2; c++)
				group[r][c] = map[r + row][c + col];
		}
		
		return group;
	}
	
	public static void main(String[] args) throws IOException {
		init();
		
		//4. 연산 순서에 특정 연산 수행
		for (int idx = 0; idx < operationSize; idx++)
			Operation.of(operations[idx]);
		
		//5. 출력
    	for (int[] line : map) {
    		for (int element : line)
    			output.append(element).append(" ");
    		output.append("\n");
    	}
		System.out.println(output);
	}
	
	public static void init() throws IOException {
		//1. 배열의 세로, 가로 크기 그리고 연산의 수 입력
		st = new StringTokenizer(input.readLine().trim());
		rowSize = Integer.parseInt(st.nextToken());
		colSize = Integer.parseInt(st.nextToken());
		operationSize = Integer.parseInt(st.nextToken());
		
		//2. 배열의 세로 크기만큼 배열의 원소 입력
		map = new int[rowSize][colSize];
		for (int row = 0; row < rowSize; row++) {
			st = new StringTokenizer(input.readLine().trim());
			for (int col = 0; col < colSize; col++)
				map[row][col] = Integer.parseInt(st.nextToken());
		}
		
		//3. 연산의 수만큼 연산 입력
		operations = new int[operationSize];
		st = new StringTokenizer(input.readLine().trim());
		for (int idx = 0; idx < operationSize; idx++)
			operations[idx] = Integer.parseInt(st.nextToken());
	}
}
