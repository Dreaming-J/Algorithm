/*
#SWEA 2805. 농작물 수확하기

0. 테스트 케이스 입력
1. 농장의 크기 입력
2. 농장의 크기만큼 농장물의 가치 입력
3. 변수 초기화
4. 규칙에 따라 수익 계산
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static int testCases;
    static int farmSize;
    static int[][] farm;
    static int avenue;
    
    public static void initTestCase() throws IOException {
    	//1. 농장의 크기 입력
        farmSize = Integer.parseInt(input.readLine());
        
    	//2. 농장의 크기만큼 농장물의 가치 입력
        farm = new int[farmSize][farmSize];
        for (int row = 0; row < farmSize; row++) {
        	String line = input.readLine();
        	for (int col = 0; col < farmSize; col++) {
        		farm[row][col] = line.charAt(col) - '0';
        	}
        }
        
        //3. 변수 초기화
        avenue = 0;
    }
    
    //4. 규칙에 따라 수익 계산
    public static void getAvenue() {
        for (int row = 0; row < farmSize; row++) {
            int gap = Math.abs(farmSize / 2 - row);
            for (int col = 0; col < farmSize - 2 * gap; col++) {
                avenue += farm[row][col + gap];
            }
        }
    }

    public static void main(String args[]) throws IOException {
    	//0. 테스트 케이스 입력
        testCases = Integer.parseInt(input.readLine());
        for(int tc = 1; tc <= testCases; tc++) {
        	initTestCase();

        	//4. 규칙에 따라 수익 계산
        	getAvenue();
        	
            output.append("#").append(tc).append(" ").append(avenue).append("\n");
        }
        System.out.println(output);
    }
}