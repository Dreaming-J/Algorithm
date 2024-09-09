/*
= BOJ 1806. 부분합

= 로직
1. 초기 세팅
	1-1. 변수 초기화
	1-2. 수열의 길이, 목표 부분합 입력
	1-3. 수열 입력
		1-3-1. 목표 숫자가 존재하는지 확인
2. 목표 부분합 찾기
	2-1. 목표 부분합을 원소로 가지고 있다면 리턴
3. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int numberSize, targetSum;
    static int[] numbers;
    static int length;
    
    //2. 목표 부분합 찾기
    public static void findTargetSum() {
    	//2-1. 목표 부분합을 원소로 가지고 있다면 리턴
    	if (length == 1)
    		return;
    	
    	int sum = 0;
    	for (int start = 0, end = 0; ;) {
    		if (sum < targetSum) {
    			sum += numbers[end++];
    		}
    		else if (sum >= targetSum) {
    			length = Math.min(end - start, length);
    			sum -= numbers[start++];
    		}
    		
    		if (end == numberSize && sum < targetSum)
    			return;
    	}
    }
    
    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        initTestCase();
        
        //2. 목표 부분합 찾기
        findTargetSum();

        //3. 출력
        System.out.print(length == Integer.MAX_VALUE ? 0 : length);
    }

    //1. 초기 세팅
    public static void initTestCase() throws IOException {
    	//1-1. 변수 초기화
    	length = Integer.MAX_VALUE;
    	
    	//1-2. 수열의 길이, 목표 부분합 입력
    	st = new StringTokenizer(input.readLine());
    	numberSize = Integer.parseInt(st.nextToken());
    	targetSum = Integer.parseInt(st.nextToken());
    	
    	//1-3. 수열 입력
    	numbers = new int[numberSize];
    	st = new StringTokenizer(input.readLine());
    	for (int idx = 0; idx < numberSize; idx++) {
    		numbers[idx] = Integer.parseInt(st.nextToken());
    		
    		//1-3-1. 목표 숫자가 존재하는지 확인
    		if (numbers[idx] == targetSum)
    			length = 1;
    	}
    }
}