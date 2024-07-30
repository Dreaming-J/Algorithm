/*
#SWEA 5432. 쇠막대기 자르기
여러 개의 쇠막대기 아래에서 위로 겹쳐 놓고, 레이저를 위에서 수직으로 발사하여 절단하려 한다.
쇠막대기와 레이저의 배치는 다음 조건을 만족한다.
    - 쇠막대기는 자신보다 긴 쇠막대기 위에만 놓일 수 있다.
    - 쇠막대기를 다른 쇠막대기 위에 놓는 경우 완전히 포함되도록 놓되, 끝점은 겹치지 않도록 놓는다.
    - 각 쇠막대기를 자르는 레이저는 적어도 하나 존재한다.
    - 레이저는 어떤 쇠막대기의 양 끝점과도 겹치지 않는다.
이러한 배치는 괄호를 이용하여 표현할 수 있다.
    1. 레이저는 여는 괄호와 닫는 괄호의 인접한 쌍 "()"으로 표혀된다.
        모든 "()"는 반드시 레이저를 표현한다.
    2. 쇠막대기의 왼쪽끝은 여는 괄호 '('로, 오른쪽 끝은 닫힌 괄호 ')'로 표현된다.
잘려진 쇠막대기 조각의 총 개수를 구해라.

#입력
첫째 줄: 테스트 케이스의 수 T
각 테스트 케이스 첫번째 줄: 쇠막대기와 레이저의 배치를 나타내는 괄호 표현, 공백 X, 최대 길이 100_000
#출력
"#Ti [잘려진 조각의 총 개수]"

#로직
스택을 이용하여 푼다.
쇠막대기의 열린 괄호는 카운팅하며, 잘려질 쇠막대기의 개수 의미한다.
레이저를 만난 시점에 카운트를 하나 줄인 후, 카운트 수 만큼 쇠막대기가 잘리게 된다.
쇠막대기의 닫힌 괄호는 카운트를 하나 줄인 후, 1개의 쇠막대기가 잘리게 된다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Solution {
    static final Character OPEN_BAR = '(', CLOSED_BAR = ')';
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int testCases;
    static String bar;
    static char prevBar, curBar;
    static int slicingCount, slicedCount; //slicingCount 현재 잘리고 있는 막대기의 수, slicedCount 잘려진 막대기의 수

    public static void main(String args[]) throws IOException {
        testCases = Integer.parseInt(input.readLine());
        for(int test_case = 1; test_case <= testCases; test_case++) {
            //입력 및 초기화
        	bar = input.readLine();
        	
            //로직
        	slicedCount = 0;
        	for (int idx = 0; idx < bar.length(); idx++) {
        		prevBar = curBar;
        		curBar = bar.charAt(idx);
        		//막대기의 시작을 만났을 때
        		if (curBar == OPEN_BAR) {
        			slicingCount++;
        		}
        		if (curBar == CLOSED_BAR) {
        			//레이저를 만났을 때
        			if (prevBar == OPEN_BAR) slicedCount += --slicingCount;
        			//막대기의 끝을 만났을 때
        			else {
        				slicingCount--;
        				slicedCount++;
        			}
        		}
        	}

            //출력
            System.out.printf("#%d %d\n", test_case, slicedCount);
        }
    }
}