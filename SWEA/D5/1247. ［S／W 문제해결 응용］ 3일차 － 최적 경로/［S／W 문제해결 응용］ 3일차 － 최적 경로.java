/*
SWEA 1247. [S/W 문제해결 응용] 3일차 - 최적 경로

0. 테스트 케이스 입력
1. 초기 세팅
    1-1. 고객의 수 입력
    1-2. 회사, 집, N명의 고객 총 N+2개의 좌표 입력
2. dfs + dp를 이용한 방문 판매
    2-1. 모든 고객을 방문했다면, 집과의 거리 반환
    2-2. 현재 방문할 고객의 집에서 집까지의 최소값을 가지고 있다면 해당 값 반환
    2-3. 다른 고객의 집 방문
        2-3-1. 방문한 적 있으면 패스
        2-3-2. 현재 위치에서 다음 고객의 집까지의 거리 계산
        2-3-3. 최소 거리 업데이트
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int FIRM_INDEX = 0, HOME_INDEX = 1;
    static int testCase;
    static int customerSize;
    static Position[] customers;
    static int[][] memo; // [방문 번호][방문한 집들 bit] = 최소거리

    public static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void initTestCase() throws IOException {
        //1-1. 고객의 수 입력
        customerSize = Integer.parseInt(input.readLine().trim());

        //1-2. 회사, 집, N명의 고객 총 N+2개의 좌표 입력
        st = new StringTokenizer(input.readLine().trim());
        customers = new Position[customerSize + 2];
        customers[FIRM_INDEX] = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        customers[HOME_INDEX] = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        for (int idx = 2; idx < customers.length; idx++) {
            customers[idx] = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        //1-3. 변수 초기화
        memo = new int[customers.length][(1 << customers.length) - 1];
    }

    
    //2. dfs + dp를 이용한 방문 판매
    public static int findBestRoute(int customerIdx, int visitBit) {
        //2-1. 모든 고객을 방문했다면, 집과의 거리 반환
        if (visitBit == (1 << customers.length) - 1) {
            return getDistance(customerIdx, HOME_INDEX);
        }

        //2-2. 현재 방문할 고객의 집에서 집까지의 최소값을 가지고 있다면 해당 값 반환
        if (memo[customerIdx][visitBit] != 0)
            return memo[customerIdx][visitBit];

        memo[customerIdx][visitBit] = Integer.MAX_VALUE;
        //2-3. 다른 고객의 집 방문
        for (int nextCustomer = 2; nextCustomer < customers.length; nextCustomer++) {
            //2-3-1. 방문한 적 있으면 패스
            if ((visitBit & 1 << nextCustomer) != 0)
                continue;

            //2-3-2. 현재 위치에서 다음 고객의 집까지의 거리 계산
            int distance = findBestRoute(nextCustomer, visitBit | 1 << nextCustomer) +
                    getDistance(customerIdx, nextCustomer);

            //2-3-3. 최소 거리 업데이트
            memo[customerIdx][visitBit] = Math.min(distance, memo[customerIdx][visitBit]);
        }

        return memo[customerIdx][visitBit];
    }

    public static int getDistance(int idx1, int idx2) {
        return Math.abs(customers[idx1].x - customers[idx2].x) + Math.abs(customers[idx1].y - customers[idx2].y);
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            initTestCase();

            //3. 출력
            output.append("#").append(tc).append(" ").append(findBestRoute(FIRM_INDEX, 0b11)).append("\n");
        }
        System.out.println(output);
    }
}