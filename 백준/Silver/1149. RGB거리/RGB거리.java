/*
= BOJ 1149. RGB거리

= 특이 사항
DP

= 로직
1. 초기 세팅
	1-1. 집의 개수 입력
	1-2. RGB 색칠 비용 입력
	1-3. 변수 초기화
2. 최소 비용 계산
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int COLOR_SIZE = 3, RED = 0, GREEN = 1, BLUE = 2;
    static int houseSize;
    static int[][] paintCost, minCost;

    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
    	init();
    	
    	//2. 최소 비용 계산
    	for (int houseIdx = 1; houseIdx <= houseSize; houseIdx++) {
    		for (int colorIdx = 0; colorIdx < COLOR_SIZE; colorIdx++) {
    			minCost[houseIdx][colorIdx] = Math.min(minCost[houseIdx - 1][(colorIdx + 1) % COLOR_SIZE], minCost[houseIdx - 1][(colorIdx + 2) % COLOR_SIZE]) + paintCost[houseIdx][colorIdx];
    		}
    	}
    	
    	//3. 출력
        System.out.println(Math.min(Math.min(minCost[houseSize][RED], minCost[houseSize][GREEN]), minCost[houseSize][BLUE]));
    }
    
	//1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 집의 개수 입력
    	houseSize = Integer.parseInt(input.readLine());
    	
    	//1-2. RGB 색칠 비용 입력
    	paintCost = new int[houseSize + 1][COLOR_SIZE];
    	for (int houseIdx = 1; houseIdx <= houseSize; houseIdx++) {
    		st = new StringTokenizer(input.readLine());
    		for (int colorIdx = 0; colorIdx < COLOR_SIZE; colorIdx++) {
    			paintCost[houseIdx][colorIdx] = Integer.parseInt(st.nextToken());
    		}
    	}
    	
    	//1-3. 변수 초기화
    	minCost = new int[houseSize + 1][COLOR_SIZE];
    }
}