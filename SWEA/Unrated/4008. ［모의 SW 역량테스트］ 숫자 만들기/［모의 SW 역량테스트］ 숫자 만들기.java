/*
=SWEA 4008. [모의 SW 역량테스트] 숫자 만들기

0. 테스트 케이스 입력
1. 숫자의 개수 입력
2. 사칙연산자 카드의 개수 입력
3. 개수만큼 숫자 입력
4. 주어진 개수에 맞춰 사칙연산을  중복순열
	4-1. 모든 원소를 탐색했다면, 탐색 종료
		5. 선택한 숫자의 순열과 사칙연산의 중복순열을 통해 수식 계산
	4-2. 다음 원소를 선택 
5. 선택한 숫자의 순열과 사칙연산의 중복순열을 통해 수식 계산
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int OPERATOR_SIZE = 4;
	static final int PLUS = 0, MINUS = 1, MULTIPLY = 2, DIVIDE = 3;
	static int testCase;
	static int numberSize;
	static int[] operators = new int[OPERATOR_SIZE]; // [+ - * /]의 순서로 개수 보관
	static int[] equation;
	static int maxAnswer, minAnswer;
	
	//4. 주어진 개수에 맞춰 사칙연산을  중복순열
	public static void findOperatorsPermutationWithRepeat(int selectIdx) {
		//4-1. 모든 원소를 탐색했다면, 탐색 종료
		if (selectIdx == numberSize - 1) {
			//6. 선택한 숫자의 순열과 사칙연산의 중복순열을 통해 수식 계산
			calEquation();
			return;
		}
		
		//4-2. 다음 원소를 선택 
		for (int idx = 0; idx < OPERATOR_SIZE; idx++) {
			if (operators[idx] == 0)
				continue;
			equation[selectIdx * 2 + 1] = idx;
			operators[idx]--;
			findOperatorsPermutationWithRepeat(selectIdx + 1);
			operators[idx]++;
		}
	}
	
	//5. 선택한 숫자의 순열과 사칙연산의 중복순열을 통해 수식 계산
	public static void calEquation() {
		int leftTerm = equation[0];
		
		for (int operatorIdx = 1; operatorIdx < equation.length; operatorIdx += 2) {
			switch (equation[operatorIdx]) {
				case PLUS:
					leftTerm += equation[operatorIdx + 1];
					break;
				case MINUS:
					leftTerm -= equation[operatorIdx + 1];
					break;
				case MULTIPLY:
					leftTerm *= equation[operatorIdx + 1];
					break;
				case DIVIDE:
					leftTerm /= equation[operatorIdx + 1];
					break;
			}
		}
		
		maxAnswer = Math.max(leftTerm, maxAnswer);
		minAnswer = Math.min(leftTerm, minAnswer);
	}
	
	public static void main(String[] args) throws IOException {
		//0. 테스트 케이스 입력
		testCase = Integer.parseInt(input.readLine());
		
		for (int tc = 1; tc <= testCase; tc++) {
			initTestCase();
			
			findOperatorsPermutationWithRepeat(0);
			
			output.append("#").append(tc).append(" ").append(maxAnswer - minAnswer).append("\n");
		}
		System.out.println(output);
	}
	
	private static void initTestCase() throws IOException {
		//1. 숫자의 개수 입력
		numberSize = Integer.parseInt(input.readLine());
		
		//2. 사칙연산자 카드의 개수 입력
		st = new StringTokenizer(input.readLine().trim());
		for (int idx = 0; idx < OPERATOR_SIZE; idx++)
			operators[idx] = Integer.parseInt(st.nextToken());
		
		//3. 개수만큼 숫자 입력
		equation = new int[numberSize + numberSize - 1];
		st = new StringTokenizer(input.readLine().trim());
		for (int idx = 0; idx < numberSize; idx++)
			equation[idx * 2] = Integer.parseInt(st.nextToken());
		
		maxAnswer = Integer.MIN_VALUE;
		minAnswer = Integer.MAX_VALUE;
	}
}
