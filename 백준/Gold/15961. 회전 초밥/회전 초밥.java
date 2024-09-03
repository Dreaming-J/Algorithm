/*
= BOJ 15961. 회전 초밥

= 로직
1. 초기 세팅
	1-1. 접시의 수, 초밥의 가짓수, 연속해서 먹는 접시의 수, 쿠폰 번호 입력
	1-2. 접시의 수만큼 회전 초밥의 종류 입력
	1-3. 변수 초기화
2. 초밥의 가짓수의 최댓값 계산
	2-1. 모든 조합을 확인할 때까지 반복
		2-1-1. 먹은 접시의 수가 적으면  더 먹기
		2-1-2. 먹어야 접시의 수만큼 먹었다면 먹은 스시의 종류 계산 후, 가장 마지막으로 먹었던 스시 취소하기
		2-1-3. 먹은 스시의 종류가 최댓값이라면 탐색 종료
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
    
  //2. 초밥의 가짓수의 최댓값 계산
    public static void findMaxEatSushi() {
    	eatCount[couponNo] = 1;
    	int eatSushi = 1;
    	
    	//2-1. 모든 조합을 확인할 때까지 반복
    	for (int leftIdx = 0, rightIdx = 0; leftIdx != plateSize || rightIdx != plateSize + (streakSize - 1);) {
    		//2-1-1. 먹은 접시의 수가 적으면  더 먹기
    		if (rightIdx - leftIdx < streakSize) {
    			eatSushi += eatCount[plates[rightIdx % plateSize]]++ == 0 ? 1 : 0;
    			rightIdx++;
    		}
    		
    		//2-1-2. 먹어야 접시의 수만큼 먹었다면 먹은 스시의 종류 계산 후, 가장 마지막으로 먹었던 스시 취소하기
    		else if (rightIdx - leftIdx == streakSize) {
    			maxEatSushi = Math.max(eatSushi, maxEatSushi);
    			
    			eatSushi -= eatCount[plates[leftIdx]]-- == 1 ? 1 : 0;
    			leftIdx++;
    		}
    		
    		//2-1-3. 먹은 스시의 종류가 최댓값이라면 탐색 종료
    		if (maxEatSushi == streakSize + 1)
    			return;
    	}
    }
    
    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
    	init();
    	
    	//2. 초밥의 가짓수의 최댓값 계산
    	findMaxEatSushi();
    	
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