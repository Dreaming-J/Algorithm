/*
= BOJ 2294. 동전 2

= 로직
1. 초기 세팅
	1-1. 동전의 개수와 목표 금액 입력
	1-2. 동전의 가치 입력
	1-3. 변수 초기화
2. 최소 개수 계산
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
    static final int INF = 100_000;
    static int coinSize, targetCost;
    static int[] coinValue, minCount;
    
    //2. 최소 개수 계산
    public static void calMinCount() {
    	for (int value : coinValue) {
    		for (int cost = value; cost <= targetCost; cost++) {
				minCount[cost] = Math.min(minCount[cost - value] + 1, minCount[cost]);
    		}
    	}
    }

    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
    	init();
    	
    	//2. 최소 횟수 계산
    	calMinCount();
    	
    	//3. 출력
        System.out.println(minCount[targetCost] == INF ? -1 : minCount[targetCost]);
    }
    
	//1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 동전의 개수와 목표 금액 입력
    	st = new StringTokenizer(input.readLine());
    	coinSize = Integer.parseInt(st.nextToken());
    	targetCost = Integer.parseInt(st.nextToken());
    	
    	//1-2. 동전의 가치 입력
    	coinValue = new int[coinSize];
    	for (int idx = 0; idx < coinSize; idx++) {
    		coinValue[idx] = Integer.parseInt(input.readLine());
    	}
    	
    	//1-3. 변수 초기화
    	minCount = new int[targetCost + 1];
    	Arrays.fill(minCount, INF);
    	minCount[0] = 0;
    }
}