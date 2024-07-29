/*
#SWEA 2805. 농자굴 수확하기
NxN 크기의 농장이 있고, 각 칸에는 수확 시 얻을 수 있는 수익이 적혀있다.
이 농장의 크기는 항상 홀수이고, 수확은 항상 농장의 크기에 맞는 정사각형 마름모만 가능하다.
수확을 통해 얻을 수 있는 수익은 얼마인지 구하시오.

#입력
첫째 줄: 테스트 케이스의 수 T
각 테스트 케이스 첫번째 줄: 농장의 크기 N (1 <= N <= 49, 홀수)
다음 N개의 줄: 농작 내 농작물의 가치
#출력
"#t 수익"

#로직
현재 줄과 중간 줄 사이의 간격을 gap이라고 했을 때,
현재 줄의 gap 위치부터 시작하여,(N - 2 * gap)개 만큼 수확한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int testCases;
    static int farmSize;
    static int[][] farm;
    static int answer;

    public static void main(String args[]) throws IOException {
        testCases = Integer.parseInt(input.readLine());
        for(int test_case = 1; test_case <= testCases; test_case++) {
            //입력
            farmSize = Integer.parseInt(input.readLine());
            farm = new int[farmSize][farmSize];
            for (int row = 0; row < farmSize; row++)
                farm[row] = Stream.of(input.readLine().trim().split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray();

            //로직
            answer = 0;
            for (int row = 0; row < farmSize; row++) {
                int gap = Math.abs(farmSize / 2 - row);
                for (int col = 0; col < farmSize - 2 * gap; col++) {
                    answer += farm[row][col + gap];
                }
            }

            //출력
            System.out.printf("#%d %d\n", test_case, answer);
        }
    }
}