/*
= BOJ 2467. 용액

= 로직
1. 초기 세팅
	1-1. 용액의 수 입력
	1-2. 용액의 수만큼 용액의 정보 입력
2. 투포인터를 활용하여 0에 가까운 두 용액 찾기
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int solutionSize;
    static int[] solutions, selectSolution;
    static int minValue;

    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
        init();
        
        //2. 투포인터를 활용하여 0에 가까운 두 용액 찾기
        for (int leftIdx = 0, rightIdx = solutionSize - 1; leftIdx != rightIdx;) {
        	int value = solutions[leftIdx] + solutions[rightIdx];

        	if (Math.abs(value) < minValue) {
        		minValue = Math.abs(value);
        		selectSolution[0] = solutions[leftIdx];
        		selectSolution[1] = solutions[rightIdx];
        	}
        	
        	if (value > 0)
        		rightIdx--;
        	else if (value < 0)
        		leftIdx++;
        	else
        		break;
        }
        
        //3. 출력
        System.out.println(selectSolution[0] + " " + selectSolution[1]);
    }
	
	//1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 용액의 수 입력
    	solutionSize = Integer.parseInt(input.readLine());
    	
    	//1-2. 용액의 수만큼 용액의 정보 입력
    	solutions = new int[solutionSize];
    	st = new StringTokenizer(input.readLine());
    	for (int idx = 0; idx < solutionSize; idx++)
    		solutions[idx] = Integer.parseInt(st.nextToken());
    	
    	//1-3. 변수 초기화
    	selectSolution = new int[2];
    	minValue = Integer.MAX_VALUE;
    }
}
