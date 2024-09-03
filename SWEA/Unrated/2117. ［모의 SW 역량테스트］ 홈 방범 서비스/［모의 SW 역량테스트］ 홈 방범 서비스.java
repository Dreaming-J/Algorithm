/*
= SWEA 2117. [모의 SW 역량테스트] 홈 방범 서비스

= 특이 사항
손해보지 않고, 가장 많은 집에 제공하는 경우의 집의 수 출력

= 로직
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 변수 초기화
	1-2. 도시의 크기, 지불 비용 입력
	1-3. 도시의 크기만큼 정보 입력 (1 = 집, 0 = 빈 곳)
	    1-3-1. 집의 개수 카운트
2. 서비스를 제공할 수 있는 가장 많은 집의 개수 계산 
	2-1. 운영 범위에 내부의 집 갯수 계산
	2-2. 서비스 크기에 따른 운영 비용 계산
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
    static final int HOUSE = 1;
    static int testCase;
    static int citySize, payPerHouse;
    static int[][] city;
    static int houseSize; //도시의 집 개수
    static int maxHouseCount; //서비스 받는 집의 최대 개수

    //2-1. 운영 범위에 내부의 집 갯수 계산
    public static int findHouseInBoundary(int centerRow, int centerCol, int servicePower) {
        int houseCount = 0;

        for (int row = centerRow - servicePower + 1; row <= centerRow + servicePower - 1; row++) {
            int gap = Math.abs(centerRow - row);
            for (int col = centerCol - servicePower + 1; col <= centerCol + servicePower - 1; col++) {
                if (!canGo(row, col))
                    continue;
                if (col < centerCol - servicePower + 1 + gap || col > centerCol + servicePower - 1 - gap)
                    continue;

                if (city[row][col] == HOUSE)
                    houseCount++;
            }
        }

        return houseCount;
    }

    //2-2. 서비스 크기에 따른 운영 비용 계산
    public static int calServiceCost(int servicePower) {
        return servicePower * servicePower + (servicePower - 1) * (servicePower - 1);
    }

    //2. 서비스를 제공할 수 있는 가장 많은 집의 개수 계산 
    public static void findMaxHouseCount() {
        for (int row = 0; row < citySize; row++) {
            for (int col = 0; col < citySize; col++) {
                for (int servicePower = 1; servicePower <= citySize + 2; servicePower++) {
                	//2-1. 운영 범위에 내부의 집 갯수 계산
                    int houseCount = findHouseInBoundary(row, col, servicePower);

                    //2-2. 서비스 크기에 따른 운영 비용 계산
                    if (calServiceCost(servicePower) <= houseCount * payPerHouse)
                        maxHouseCount = Math.max(houseCount, maxHouseCount);

                    if (maxHouseCount >= houseSize)
                        return;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();

            //2. 서비스를 제공할 수 있는 가장 많은 집의 개수 계산 
            findMaxHouseCount();

            //3. 출력
            output.append("#").append(tc).append(" ").append(maxHouseCount).append("\n");
        }
        System.out.println(output);
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < citySize && col >= 0 && col < citySize;
    }

    public static void initTestCase() throws IOException {
        //1-1. 변수 초기화
        houseSize = 0;
        maxHouseCount = Integer.MIN_VALUE;

        //1-2. 도시의 크기, 지불 비용 입력
        st = new StringTokenizer(input.readLine().trim());
        citySize = Integer.parseInt(st.nextToken());
        payPerHouse = Integer.parseInt(st.nextToken());

        //1-3. 도시의 크기만큼 정보 입력 (1 = 집, 0 = 빈 곳)
        city = new int[citySize][citySize];
        for (int row = 0; row < citySize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < citySize; col++) {
                city[row][col] = Integer.parseInt(st.nextToken());

                //1-3-1. 집의 개수 카운트
                if (city[row][col] == HOUSE) {
                    houseSize++;
                }
            }
        }
    }
}