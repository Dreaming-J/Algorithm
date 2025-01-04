/*
= BOJ 1480. 보석 모으기

- 특이사항
보석의 개수: 1 ~ 13
가방의 개수: 1 ~ 10
가방의 용량: 2 ~ 20
보석의 무게: 1 ~ 20
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int NOT_VISITED = -1;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int jewelSize, bagSize, bagCapacity;
    static int[] jewels;
    static int[][][] memo;

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(stealJewel(0, bagCapacity, 0));
    }

    private static int stealJewel(int bagIdx, int curCapacity, int jewelVisitedBit) {
        //모든 보석 방문했거나 모든 가방을 사용했을 경우
        if (jewelVisitedBit == (1 << jewelSize) - 1 || bagIdx == bagSize)
            return 0;

        //이미 현재 상태에서의 최대 개수를 알고 있다면
        if (memo[bagIdx][curCapacity][jewelVisitedBit] != NOT_VISITED)
            return memo[bagIdx][curCapacity][jewelVisitedBit];

        int maxCount = 0;

        //다음 보석 선택
        for (int jewelIdx = 0; jewelIdx < jewelSize; jewelIdx++) {
            //이미 사용한 보석이라면
            if ((jewelVisitedBit & 1 << jewelIdx) != 0)
                continue;

            //가방 용량이 보석 용량보다 크다면 가방에 보석을 담고 현재 가방 유지
            if (curCapacity > jewels[jewelIdx])
                maxCount = Math.max(stealJewel(bagIdx, curCapacity - jewels[jewelIdx], jewelVisitedBit | 1 << jewelIdx) + 1, maxCount);

            //가방 용량이 보석 용량 이상이라면 가방에 보석을 담고 다음 가방 이동
            if (curCapacity >= jewels[jewelIdx])
                maxCount = Math.max(stealJewel(bagIdx + 1, bagCapacity, jewelVisitedBit | 1 << jewelIdx) + 1, maxCount);
        }

        return memo[bagIdx][curCapacity][jewelVisitedBit] = maxCount;
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        jewelSize = Integer.parseInt(st.nextToken());
        bagSize = Integer.parseInt(st.nextToken());
        bagCapacity = Integer.parseInt(st.nextToken());

        jewels = new int[jewelSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < jewelSize; idx++)
            jewels[idx] = Integer.parseInt(st.nextToken());

        memo = new int[bagSize][bagCapacity + 1][1 << jewelSize];
        for (int[][] grid : memo) {
            for (int[] array : grid)
                Arrays.fill(array, NOT_VISITED);
        }
    }
}