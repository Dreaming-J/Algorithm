/*
#SWEA 10726. 이진수 표현
정수 N, M이 주어질 때, M의 이진수 표현의 마지막 N 비트가 모두 1로 켜져 있는지를 출력하라

#입력
첫째 줄: 테스트 케이스의 수 T
    =각 테스트 케이스
    첫번째 줄: N, M (1 <= N <= 30, 0 <= M <= 10^8)
#출력
"#Ti [마지막 N 비트 모두 켜짐 유무]"
    true면 ON으로 false면 OFF로 출력

#로직
N비트의 값 decN을 구한 후, M % (decN + 1)이 decN과 같다면 정답
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Solution {

    public static void main(String args[]) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int testCases;
        int bit, number;
        String answer;

        testCases = Integer.parseInt(input.readLine());
        for (int test_case = 1; test_case <= testCases; test_case++) {
            //입력 및 초기화
            st = new StringTokenizer(input.readLine().trim());
            bit = Integer.parseInt(st.nextToken());
            number = Integer.parseInt(st.nextToken());

            //로직
            answer = number % (1 << bit) == (1 << bit) - 1 ? "ON" : "OFF";

            //출력
            output.write(String.format("#%d %s\n", test_case, answer));
        }

        output.flush();
        output.close();
    }
}