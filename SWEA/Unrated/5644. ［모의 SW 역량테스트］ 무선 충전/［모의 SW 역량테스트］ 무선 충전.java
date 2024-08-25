/*
SWEA 1247. [S/W 문제해결 응용] 3일차 - 최적 경로

0초부터 충전 가능
같은 위치의 BC는 없지만, 같은 위치의 사용자는 가능
한 BC에 두 사용자가 있으면, 균등하게 분배
모든 사용자가 충전한 양의 합의 최댓값을 구해라

0. 테스트 케이스 입력
1. 초기 세팅
    1-1. 이동 시간, BC의 개수 입력
    1-2. 사용자 A와 B의 이동 정보 입력
    1-3. BC의 좌표, 충전 범위, 성능 순으로 BC 정보 입력
    1-4. 변수 초기화
2. 무선 충전 시뮬레이션 실행
    2-1. 사용자 이동
    2-2. 이 시점의 최대 충전량 계산 -> 3번으로 이동
    2-3. 최대 충전량에 누적
3. 최대 충전량 계산
    3-1. 사용자 A, B가 충전기를 선택했다면 현재 최대 충전량 업데이트 후 종료
    3-2. 충전기 선택
        3-2-1. 충전 거리가 닿지 않는다면 패스
        3-2-2. 충전기 선택 후, 다음 사용자 차례로 이동
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int USER_SIZE = 2, A = 0, B = 1;
    static final int[][] deltas = {{0, 0}, {0, -1}, {1, 0}, {0, 1}, {-1, 0}}; //(x,y)로 상하좌우 델타 배열
    static int testCase;
    static int totalTime, chargerSize;
    static int[][] moveInfo;
    static Position[] user;
    static Charger[] chargers;
    static Charger[] currentSelectCharger;
    static int currentMaxChargeAmount, totalMaxChargeAmount;

    //3. 최대 충전량 계산
    public static void findMaxChargeAmount(int userIdx) {
        //3-1. 사용자 A, B가 충전기를 선택했다면 현재 최대 충전량 업데이트 후 종료
        if (userIdx == USER_SIZE) {
            currentMaxChargeAmount = Math.max(calChargeAmount(), currentMaxChargeAmount);
            return;
        }

        //3-2. 충전기 선택
        for (int chargerIdx = 0; chargerIdx <= chargerSize; chargerIdx++) {
            //3-2-1. 충전 거리가 닿지 않는다면 패스
            if (getDistance(user[userIdx], chargers[chargerIdx]) > chargers[chargerIdx].coverage)
                continue;

            //3-2-2. 충전기 선택 후, 다음 사용자 차례로 이동
            currentSelectCharger[userIdx] = chargers[chargerIdx];
            findMaxChargeAmount(userIdx + 1);
        }
    }

    //선택된 충전기로 인한 충전량 반환
    public static int calChargeAmount() {
        //동일한 충전기를 선택했을 경우
        if (currentSelectCharger[A] == currentSelectCharger[B])
            return currentSelectCharger[A].performance;

        //서로 다른 충전기를 선택했을 경우
        return currentSelectCharger[A].performance + currentSelectCharger[B].performance;
    }

    //사용자와 충전기 사이의 거리 반환
    public static int getDistance(Position p1, Position p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            initTestCase();

            //2. 무선 충전 시뮬레이션 실행
            for (int time = 0; time <= totalTime; time++) {
                //2-1. 사용자 이동
                user[A].move(deltas[moveInfo[A][time]]);
                user[B].move(deltas[moveInfo[B][time]]);

                //2-2. 이 시점의 최대 충전량 계산 -> 3번으로 이동
                currentMaxChargeAmount = Integer.MIN_VALUE;
                currentSelectCharger[A] = chargers[0];
                currentSelectCharger[B] = chargers[0];
                findMaxChargeAmount(0);

                //2-3. 최대 충전량에 누적
                totalMaxChargeAmount += currentMaxChargeAmount;
            }

            //4. 출력
            output.append("#").append(tc).append(" ").append(totalMaxChargeAmount).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
        //1-1. 이동 시간, BC의 개수 입력
        st = new StringTokenizer(input.readLine().trim());
        totalTime = Integer.parseInt(st.nextToken());
        chargerSize = Integer.parseInt(st.nextToken());

        //1-2. 사용자 A와 B의 이동 정보 입력
        moveInfo = new int[USER_SIZE][totalTime + 1];
        for (int idx = 0; idx < USER_SIZE; idx++) {
            st = new StringTokenizer(input.readLine().trim());

            for (int time = 1; time <= totalTime; time++) {
                moveInfo[idx][time] = Integer.parseInt(st.nextToken());
            }
        }

        //1-3. BC의 좌표, 충전 범위, 성능 순으로 BC 정보 입력
        chargers = new Charger[chargerSize + 1];
        for (int idx = 0; idx < chargerSize; idx++) {
            st = new StringTokenizer(input.readLine().trim());

            chargers[idx] = new Charger(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        chargers[chargerSize] = new Charger(0, 0, 20, 0); //더미 충전기

        //1-4. 변수 초기화
        user = new Position[2];
        user[A] = new Position(1, 1);
        user[B] = new Position(10, 10);

        currentSelectCharger = new Charger[USER_SIZE];

        totalMaxChargeAmount = 0;
    }

    public static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move(int[] delta) {
            x += delta[0];
            y += delta[1];
        }
    }

    public static class Charger extends Position {
        int coverage;
        int performance;

        public Charger(int x, int y, int coverage, int performance) {
            super(x, y);
            this.coverage = coverage;
            this.performance = performance;
        }
    }
}