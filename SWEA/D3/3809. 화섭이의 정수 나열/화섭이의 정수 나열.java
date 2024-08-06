/*
#SWEA 3809. 화섭이의 정수 나열
유한한 정수열을 끊었을 때, 만들 수 없으면서 가장 작은 정수를 구해라.

#입력
첫째 줄: 테스트 케이스의 수 T
    =각 테스트 케이스
    첫번째 줄: 유한한 정수열 길이 N (1 <= N <= 10^3)
    두번째 줄부터 ?개의 줄: d가 공백 또는 줄바꿈으로 구분되어 있음
#출력
각 테스트 케이스마다 만들 수 없는 가장 작은 정수를 출력

#로직
N개의 정수열을 string으로 저장 후, 0부터 포함하고 있는지 판단한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Solution {

    public static void main(String args[]) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        StringBuilder sequenceBuilder;
        String sequence;
        int testCases, sequenceSize, minNum;

        testCases = Integer.parseInt(input.readLine());
        for (int test_case = 1; test_case <= testCases; test_case++) {
            //입력 및 초기화
            minNum = 0;
            sequenceSize = Integer.parseInt(input.readLine());

            sequenceBuilder = new StringBuilder();
            while (sequenceSize > 0) {
                String line = input.readLine();
                for (String c : line.split(" ")) {
                    sequenceBuilder.append(c);
                    sequenceSize--;
                }
            }
            sequence = sequenceBuilder.toString();

            //로직
            while (sequence.contains(String.valueOf(minNum))) {
                minNum++;
            }

            //출력
            output.append("#").append(test_case).append(" ").append(minNum).append("\n");
        }

        System.out.println(output.toString());
    }
}