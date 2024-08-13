import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/*
=SWEA 1225. [S/W 문제해결 기본] 7일차 - 암호생성기

1. 데이터 입력을 받는다.
	1-1. 가장 최솟값을 구한다.
2. 모든 데이터를 (최솟값 / 15 - 1) * 15만큼 빼준다.
3. 0번 데이터부터 사이클을 돌려 0이 되는 값을 찾는다.
4. 0이 되는 인덱스의 다음 칸부터 시작하여 모든 리스트를 출력한다.
 */

public class Solution {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int DATA_SIZE = 8;
	static final int CYCLE_SIZE = 5;
	static int[] data = new int[DATA_SIZE];
	static int minNum = Integer.MAX_VALUE;
	
	public static void inputTestCase() throws IOException {
		//1. 데이터 입력을 받는다.
		st = new StringTokenizer(input.readLine().trim());
		for (int idx = 0; idx < DATA_SIZE; idx++) {
			data[idx] = Integer.parseInt(st.nextToken());
			//1-1. 가장 최솟값과 인덱스를 구한다.
			if (data[idx] < minNum) {
				minNum = data[idx];
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		for (int testCase = 1; testCase <= 10; testCase++) {
			input.readLine(); //테스크 케이스의 번호는 필요없으니 생략
			inputTestCase();
			
			//2. 모든 데이터를 (최솟값 / 15 - 1) * 15만큼 빼준다.
			for (int idx = 0; idx < DATA_SIZE; idx++)
				data[idx] -= (minNum / 15 - 1) * 15;
			
			int dataIdx = 0;
			int cycleIdx = 0;
			while (true) {
				data[dataIdx] -= ++cycleIdx;
				
				if (data[dataIdx] <= 0) {
					data[dataIdx] = 0;
					break;
				}
				
				dataIdx = (dataIdx + 1) % DATA_SIZE;
				cycleIdx %= CYCLE_SIZE;
			}

			//4. 최소로 빼야 하는 횟수의 바로 다음 인덱스부터 시작하여 모든 리스트를 출력한다.
			output.append("#").append(testCase);
			for (int bias = 1; bias <= DATA_SIZE; bias++) {
				int idx = (dataIdx + bias) % DATA_SIZE;
				output.append(" ").append(data[idx]);
			}
			output.append("\n");
		}
		
		System.out.println(output);
	}
}
