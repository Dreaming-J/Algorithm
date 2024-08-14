/*
=SWEA 5215. 햄버거 다이어트

0. 테스트 케이스를 입력받는다.
1. 입력
	1-1. 재료의 수, 제한 칼로리를 입력받는다.
	1-2. N개의 줄에 걸쳐 재료의 점수와 칼로리를 입력받는다.
2. 초기화
3. 재료의 부분집합을 구한다.
	3-1. 제한 칼로리를 넘어가는 조합은 구하지 않는다.
	3-2. 모든 재료를 탐색했다면 종료한다.
		3-2-1. 최고 점수라면 갱신한다.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

public class Solution {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static int ingredientCount, limitKcal, maxScore;
	static int[][] ingredient; //점수, 칼로리 보관
	
	public static void initTestCase() throws IOException {
		//1. 입력
		//1-1. 재료의 수, 제한 칼로리를 입력받는다.
		st = new StringTokenizer(input.readLine().trim());
		ingredientCount = Integer.parseInt(st.nextToken());
		limitKcal = Integer.parseInt(st.nextToken());
		
		//1-2. N개의 줄에 걸쳐 재료의 점수와 칼로리를 입력받는다.
		ingredient = new int[ingredientCount][2];
		for (int idx = 0; idx < ingredientCount; idx++) {
			st = new StringTokenizer(input.readLine().trim());
			ingredient[idx][0] = Integer.parseInt(st.nextToken());
			ingredient[idx][1] = Integer.parseInt(st.nextToken());
		}

		//2. 초기화
		maxScore = Integer.MIN_VALUE;
	}
	
	public static void powerSet(int elementIdx, int kcal, int score) {
		//3-1. 제한 칼로리를 넘어가는 조합은 구하지 않는다.
		if (kcal > limitKcal)
			return;
		
		//3-2. 모든 재료를 탐색했다면 종료한다.
		if (elementIdx == ingredientCount) {
			//3-2-1. 최고 점수라면 갱신한다.
			maxScore = Math.max(score , maxScore);
			return;
		}
		
		powerSet(elementIdx + 1, kcal + ingredient[elementIdx][1], score + ingredient[elementIdx][0]);
		powerSet(elementIdx + 1, kcal, score);
	}
	
    public static void main(String[] args) throws IOException {
    	//0. 테스트 케이스를 입력받는다.
    	int testCase = Integer.parseInt(input.readLine());
    	
    	for (int tc = 1; tc <= testCase; tc++) {
    		initTestCase();

			//3. 재료의 부분집합을 구한다.
            powerSet(0, 0, 0);
    		
    		output.append("#").append(tc).append(" ").append(maxScore).append("\n");
    	}
    	System.out.println(output);
    }
}