/*
#SWEA 3499. 퍼펙트 셔플
N개의 카드를 퍼펙트 셔플했을 때의 순서를 출력해라
퍼펙트 셔플은 덱은 절반으로 나누고 나눈 것들을 교대로 다시 쌓는 것을 의미한다.
N이 홀수라면, 먼저 놓는 쪽에 한 장이 더 들어간다.

#입력
첫째 줄: 테스트 케이스의 수 T
	=각 테스트 케이스
	첫번째 줄: 카드의 개수 N (1 <= N <= 1000)
	두번째 줄: 카드의 이름 N개, 공백으로 구분 (길이 80이하)
#출력
퍼펙트 셔플한 결과를 한 줄로 공백으로 구분하여 출력

#로직
중간 값을 구한 후, 시작 -> 중간값 순으로 순차적으로 출력한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;

class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int testCases;
    static int cardCount;
    static String[] cards;
    static int centerIdx;
    static StringJoiner answerCards;

    public static void main(String args[]) throws IOException {
        testCases = Integer.parseInt(input.readLine());
        for(int test_case = 1; test_case <= testCases; test_case++) {
            //입력 및 초기화
        	answerCards = new StringJoiner(" ");
        	cardCount = Integer.parseInt(input.readLine());
        	cards = input.readLine().split(" ");
        	centerIdx = (int) Math.ceil((double) cardCount / 2);
        	
            //로직
        	for (int idx = 0; idx < centerIdx; idx++) {
        		answerCards.add(cards[idx]);
        		if (idx + centerIdx < cardCount)
        			answerCards.add(cards[idx + centerIdx]);
        	}
        	
            //출력
            System.out.printf("#%d %s\n", test_case, answerCards.toString());
        }
    }
}