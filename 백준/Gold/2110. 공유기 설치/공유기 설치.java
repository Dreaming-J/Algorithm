/*
= BOJ 2110. 공유기 설치

= 로직
1. 초기 세팅
    1-1. 집의 개수, 공유기의 개수 입력
    1-2. 집의 위치 입력
2. 최대 길이 탐색
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int houseSize, routerSize;
    static int[] houses;
    static int answer;

    //2. 최대 길이 탐색
    public static void findMaxLength(int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;

            if (calRouterCount(mid) >= routerSize) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        answer = right;
    }

    public static int calRouterCount(int length) {
        int routerCnt = 1;
        int range = length + houses[0];

        for (int idx = 0; idx < houseSize; idx++) {
            if (houses[idx] >= range) {
                range = houses[idx] + length;
                routerCnt++;
            }
        }

        return routerCnt;
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 최대 길이 탐색
        findMaxLength(1, houses[houseSize - 1]);

        //3. 출력
        System.out.print(answer);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 집의 개수, 공유기의 개수 입력
        st = new StringTokenizer(input.readLine());
        houseSize = Integer.parseInt(st.nextToken());
        routerSize = Integer.parseInt(st.nextToken());

        //1-2. 집의 위치 입력
        houses = new int[houseSize];
        for (int idx = 0; idx < houseSize; idx++) {
            houses[idx] = Integer.parseInt(input.readLine());
        }
        Arrays.sort(houses);
    }
}
