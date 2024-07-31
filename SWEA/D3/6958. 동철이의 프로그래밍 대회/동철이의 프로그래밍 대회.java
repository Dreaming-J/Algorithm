/*
#SWEA 6958. 동철이의 프로그래밍 대회
N명의 사람이 M개의 문제를 풀었는지 여부가 주어질 때,
1등한 사람이 몇 명이고 몇 문제를 풀었는지 구해라.

#입력
첫째 줄: 테스트 케이스의 수 T
    =각 테스트 케이스
    첫번째 줄: N M (1 <= N, M <= 20)
    두번째 줄부터 N개의 줄: M개의 정수
        0=오답, 1=정답
#출력
"#Ti [1등의 수] [1등이 푼 문제의 수]"

#로직
각 사람 별 맞춘 문제의 수를 보관
맞춘 문제를 탐색하며,
    현재 최대값과 동일한 값을 찾으면, 1등의 수 증가
    새로운 최대값을 찾으면, 최대값 업데이트 및 1등의 수 초기화
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.Stream;

class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int testCases;
    static int persons, problems;
    static int[] answerCount;
    static int firstCount, maxAnswer;

    public static void main(String args[]) throws IOException {
        testCases = Integer.parseInt(input.readLine());
        for(int test_case = 1; test_case <= testCases; test_case++) {
            //입력 및 초기화
            firstCount = 0;
            maxAnswer = 0;

            st = new StringTokenizer(input.readLine());
            persons = Integer.parseInt(st.nextToken());
            problems = Integer.parseInt(st.nextToken());
            answerCount = new int[persons];
            for (int idx = 0; idx < persons; idx++)
                answerCount[idx] = Stream.of(input.readLine().trim().split(" "))
                        .mapToInt(Integer::parseInt)
                        .sum();

            //로직
            for (int idx = 0; idx < persons; idx++) {
                if (answerCount[idx] == maxAnswer)
                    firstCount++;
                if (answerCount[idx] > maxAnswer) {
                    firstCount = 1;
                    maxAnswer = answerCount[idx];
                }
            }

            //출력
            System.out.printf("#%d %d %d\n", test_case, firstCount, maxAnswer);
        }
    }
}