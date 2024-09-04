/*
= BOJ 1463. 1로 만들기

= 특이 사항
DP
역으로 1부터 커지며 최소 횟수를 찾는다. 

= 로직
1. 초기 세팅
	1-1. 목표값 입력
	1-2. 변수 초기화
2. 최소 횟수 계산
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
    static final int OPERATION_SIZE = 3;
    static int targetNumber;
    static int[] minCount;
    
  //2. 최소 횟수 계산
    public static void calMinCount() {
    	minCount[1] = 0;
    	
    	for (int num = 1; num < targetNumber; num++) {
    		//operation: 0 = 1 더하기, 1 = 2 곱하기, 2 = 3 곱하기 
    		for (int operation = 0; operation < OPERATION_SIZE; operation++) {
    			int next = num + Math.max(num * operation, 1);
    			
    			minCount[next] = Math.min(minCount[num] + 1, minCount[next]);
    		}
    	}
    }

    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
    	init();
    	
    	//2. 최소 횟수 계산
    	calMinCount();
    	
    	//3. 출력
        System.out.println(minCount[targetNumber]);
    }
    
	//1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 목표값 입력
    	targetNumber = Integer.parseInt(input.readLine());
    	
    	//1-2. 변수 초기화
    	minCount = new int[targetNumber * 3];
    	Arrays.fill(minCount, Integer.MAX_VALUE);
    }
}