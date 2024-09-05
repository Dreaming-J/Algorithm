/*
= BOJ 10943. 팰린드롬?

= 로직
1. 초기 세팅
	1-1. 수열의 크기 입력
	1-2. 숫자 입력
	1-3. 질문의 개수 입력
	1-4. 질문 입력
	1-5. 변수 초기화
2. 펠린드롬 확인
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int numberSize, questionSize;
    static int[] numbers;
    static int[][] questions;
    static boolean[][] isPalindrome;
    
    //2. 펠린드롬 확인
    public static void checkPalindrome() {
    	for (int length = 0; length < numberSize; length++) {
        	for (int start = 1; start <= numberSize - length; start++) {
        		int end = start + length;
        		
        		if (length > 1)
        			isPalindrome[start][end] =  isPalindrome[start + 1][end - 1] ? numbers[start] == numbers[end] : false;
        		else
        			isPalindrome[start][end] = numbers[start] == numbers[end];
        	}
    	}
    }

    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
    	init();
    	
    	//2. 펠린드롬 확인
    	checkPalindrome();
    	
    	//3. 출력
    	for (int[] question : questions) {
    		output.append(isPalindrome[question[0]][question[1]] ? 1 : 0).append("\n");
    	}
        System.out.println(output);
    }
    
	//1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 수열의 크기 입력
    	numberSize = Integer.parseInt(input.readLine());
    	
    	//1-2. 숫자 입력
    	numbers = new int[numberSize + 1];
    	st = new StringTokenizer(input.readLine());
    	for (int idx = 1; idx <= numberSize; idx++)
    		numbers[idx] = Integer.parseInt(st.nextToken());
    	
    	//1-3. 질문의 개수 입력
    	questionSize = Integer.parseInt(input.readLine());
    	
    	//1-4. 질문 입력
    	questions = new int[questionSize][2];
    	for (int idx = 0; idx < questionSize; idx++) {
        	st = new StringTokenizer(input.readLine());
    		questions[idx][0] = Integer.parseInt(st.nextToken());
			questions[idx][1] = Integer.parseInt(st.nextToken());
    	}
    	
    	//1-5. 변수 초기화
    	isPalindrome = new boolean[numberSize + 1][numberSize + 1];
    }
}