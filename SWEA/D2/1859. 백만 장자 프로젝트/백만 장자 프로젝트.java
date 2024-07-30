/*
#SWEA 1859. 백만 장자 프로젝트
하루에 1개의 원료만 구매 가능하고, 판매는 얼마든지 할 수 있다.
각 날의 매매가가 주어졌을 때, 최대 이익을 구해라.

#입력
첫째 줄: 테스트 케이스의 수 T
	=각 테스트 케이스
	첫번째 줄: 원료의 수 N (2 <= N <= 1,000,000)
	두번째 줄: 각 날의 매매가 N개, 공백으로 구분 (각 날의 매매가는 10,000 이하)
#출력
#Ti [최대 이익]

#로직
뒤에서 부터 탐색하며,
    최저가보다 크다면 차이만큼을 수익으로 낸다.
    최저가보다 작다면 새로운 최저가로 업데이트한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int testCases;
    static int count;
    static int[] stuffs;
    static long maxProfit, maxPrice;

    public static void main(String args[]) throws IOException {
        testCases = Integer.parseInt(input.readLine());
        for (int test_case = 1; test_case <= testCases; test_case++) {
            //입력 및 초기화
            count = Integer.parseInt(input.readLine());

            st = new StringTokenizer(input.readLine().trim());
            stuffs = new int[count];
            for (int idx = 0; idx < count; idx++) {
                stuffs[idx] = Integer.parseInt(st.nextToken());
            }

            //초기화
            maxProfit = 0;
            maxPrice = stuffs[count - 1];

            //로직
            for (int idx = count - 1; idx >= 0; idx--) {
                if (stuffs[idx] > maxPrice)
                    maxPrice = stuffs[idx];
                if (stuffs[idx] < maxPrice) {
                    maxProfit += maxPrice - stuffs[idx];
                }
            }

            //출력
            System.out.printf("#%d %d\n", test_case, maxProfit);
        }
    }
}