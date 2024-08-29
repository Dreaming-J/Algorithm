/*
= BOJ 15686. 치킨 배달

= 특이사항
조합

= 로직
1. 초기 세팅
	1-1. 도시의 크기, 남길 치킨집의 개수 입력
	1-2. 도시의 크기만큼 도시의 정보 입력
		1-2-1. 집의 좌표 보관
		1-2-2. 치킨집의 좌표 보관
	1-3. 변수 초기화
2. 모든 치킨 거리 계산
3. 남길 치킨집의 개수만큼 치킨집의 조합 탐색
	3-1. 치킨집을 선택을 완료했다면 치킨 거리 업데이트 후 종료
	3-2. 모든 치킨집을 탐색했다면 종료
	3-3. 현재 치킨집 선택 후 다음으로 이동
	3-4. 현재 치킨집 미선택 후 다음으로 이동
4. 출력
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
	static final int HOME = 1, CHICKEN = 2, SELECTED_CHICKEN = 3, MAX_CHICKEN_SIZE = 13;
	static int mapSize, remainedChickenSize;
	static Point[] houses, chickens;
	static int houseSize, chickenSize;
	static int[][] chickenDistances;
	static int[] selectChicken;
	static int minChickenDistance;
	
	public static class Point {
		int row;
		int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	public static void selectChicken(int chickenIdx, int selectCount) {
		//3-1. 치킨집을 선택을 완료했다면 치킨 거리 업데이트 후 종료
		if (selectCount == remainedChickenSize) {
			int totalChickenDistance = 0;
			
			for (int houseIdx = 0; houseIdx < houseSize; houseIdx++) {
				int chickenDistance = Integer.MAX_VALUE;
				for (int selectChickenIdx : selectChicken) {
					chickenDistance = Math.min(chickenDistances[houseIdx][selectChickenIdx], chickenDistance);
				}
				totalChickenDistance += chickenDistance;
			}
			
			minChickenDistance = Math.min(totalChickenDistance, minChickenDistance);
			
			return;
		}
		
		//3-2. 모든 치킨집을 탐색했다면 종료
		if (chickenIdx == chickenSize)
			return;
		
		//3-3. 현재 치킨집 선택 후 다음으로 이동
		selectChicken[selectCount] = chickenIdx;
		selectChicken(chickenIdx + 1, selectCount + 1);
		
		//3-4. 현재 치킨집 미선택 후 다음으로 이동
		selectChicken(chickenIdx + 1, selectCount);
	}
	
	public static void calChickenDistance() {
		for (int houseIdx = 0; houseIdx < houseSize; houseIdx++) {
			for (int chickenIdx = 0; chickenIdx < chickenSize; chickenIdx++) {
				Point house = houses[houseIdx];
				Point chicken = chickens[chickenIdx];
				
				chickenDistances[houseIdx][chickenIdx] = Math.abs(house.row - chicken.row) + Math.abs(house.col - chicken.col);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		initTestCase();
		
		//2. 모든 치킨 거리 계산
		calChickenDistance();
		
		//3. 남길 치킨집의 개수만큼 치킨집의 조합 탐색
		selectChicken(0, 0);
		
		//4. 출력
		System.out.println(minChickenDistance);
	}
	
	public static void initTestCase() throws IOException {
		//1-1. 도시의 크기, 남길 치킨집의 개수 입력
		st = new StringTokenizer(input.readLine());
		mapSize = Integer.parseInt(st.nextToken());
		remainedChickenSize = Integer.parseInt(st.nextToken());
		
		//1-2. 도시의 크기만큼 도시의 정보 입력
		houses = new Point[2 * mapSize];
		chickens = new Point[MAX_CHICKEN_SIZE];
		for (int row = 0; row < mapSize; row++) {
			st = new StringTokenizer(input.readLine());
			for (int col = 0; col < mapSize; col++) {
				switch (Integer.parseInt(st.nextToken())) {
					//1-2-1. 집의 좌표 보관
					case HOME:
						houses[houseSize++] = new Point(row, col);
						break;
					//1-2-2. 치킨집의 좌표 보관
					case CHICKEN:
						chickens[chickenSize++] = new Point(row, col);
						break;
				}
			}
		}
		
		//1-3. 변수 초기화
		chickenDistances = new int[houseSize][chickenSize];
		selectChicken = new int[remainedChickenSize];
		minChickenDistance = Integer.MAX_VALUE;
	}
}
