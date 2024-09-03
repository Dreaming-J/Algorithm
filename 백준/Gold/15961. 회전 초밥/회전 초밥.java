/*
= BOJ 15961. 회전 초밥

= 로직
1. 초기 세팅
	1-1. 접시의 수, 초밥의 가짓수, 연속해서 먹는 접시의 수, 쿠폰 번호 입력
	1-2. 접시의 수만큼 회전 초밥의 종류 입력
	1-3. 변수 초기화
2. 초밥의 가짓수의 최댓값 계산
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int plateSize, sushiSize, streakSize, couponNo;
    static int[] plates, eatCount;
    static int maxEatSushi;
    
    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
    	init();
    	
    	//2. 초밥의 가짓수의 최댓값 계산
    	eatCount[couponNo] = 1;
    	int eatSushi = 1;
    	for (int leftIdx = 0, rightIdx = 0; leftIdx != plateSize || rightIdx != plateSize + (streakSize - 1);) {
    		if (rightIdx - leftIdx < streakSize) {
    			eatSushi += eatCount[plates[rightIdx % plateSize]]++ == 0 ? 1 : 0;
    			rightIdx++;
    		}
    		
    		else if (rightIdx - leftIdx == streakSize) {
    			maxEatSushi = Math.max(eatSushi, maxEatSushi);
    			eatSushi -= eatCount[plates[leftIdx]]-- == 1 ? 1 : 0;
    			leftIdx++;
    		}
    	}
    	
    	//3. 출력
        System.out.println(maxEatSushi);
    }
    
	//1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 접시의 수, 초밥의 가짓수, 연속해서 먹는 접시의 수, 쿠폰 번호 입력
    	st = new StringTokenizer(input.readLine());
    	plateSize = Integer.parseInt(st.nextToken());
    	sushiSize = Integer.parseInt(st.nextToken());
    	streakSize = Integer.parseInt(st.nextToken());
    	couponNo = Integer.parseInt(st.nextToken());
    	
    	//1-2. 접시의 수만큼 회전 초밥의 종류 입력
    	plates = new int[plateSize];
    	for (int idx = 0; idx < plateSize; idx++)
    		plates[idx] = Integer.parseInt(input.readLine());
    	
    	//1-3. 변수 초기화
    	eatCount = new int[sushiSize + 1];
    }
}