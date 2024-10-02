/*
= SWEA 3307. 최장 증가 부분 수열

= 로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
	1-1. 수열의 길이 입력
	1-2. 수열의 원소 입력
	1-3. 변수 초기화
2. 최장 증가 부분 수열 찾기
3. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int arraySize;
    static int[] array, lis;
    static int length;
    
    //이진 탐색
    public static int binarySearch(int target) {
    	int start = 1;
    	int end = length;
    	
    	while (start < end) {
    		//중앙 인덱스 탐색
    		int mid = (start + end) / 2;
    		
    		//중앙 인덱스의 lis값이 목표 값보다 크다면 오른쪽 바운더리 옮기기
    		if (lis[mid] > target)
    			end = mid;
    		//중앙 인덱스의 lis값이 목표 값보다 작거나 같다면 왼쪽 바운더리 옮기기
    		else
    			start = mid + 1;
    	}
    	
    	return end;
    }

    public static void main(String[] args) throws IOException {
    	//0. 테스트 케이스 입력
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
        	init();
            
        	//2. 최장 증가 부분 수열 찾기
        	for (int idx = 1; idx <= arraySize; idx++) {
        		//lis의 마지막 값보다 크다면 lis의 제일 뒤에 추가
        		if (lis[length] < array[idx])
        			lis[++length] = array[idx];
        		//lis의 마지막 값보다 크지 않다면 이진 탐색을 통해 삽입될 위치 탐색 후, 해당 위치에 추가
        		else
        			lis[binarySearch(array[idx])] = array[idx];
        	}
        	
        	//3. 출력
            output.append("#").append(tc).append(" ").append(length).append("\n");
        }
        System.out.println(output);
    }
    
    public static void init() throws IOException {
    	//1-1. 수열의 길이 입력
    	arraySize = Integer.parseInt(input.readLine());
    	
    	//1-2. 수열의 원소 입력
    	array = new int[arraySize + 1];
    	st = new StringTokenizer(input.readLine());
    	for (int idx = 1; idx <= arraySize; idx++)
    		array[idx] = Integer.parseInt(st.nextToken());
    	
    	//1-3. 변수 초기화
    	lis = new int[arraySize + 1];
    	length = 0;
    }
}