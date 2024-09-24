/*
= BOJ 17182. 우주 탐사선

= 특이사항
플로이드-워셜
비트마스킹 dp
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int planetSize, start;
    static int[][] time;
    static int[][] visitedDp;
    static int minTime;

    //모든 행성간의 최소 시간 찾기
    public static void findMinTime() {
        for (int layOver = 0; layOver < planetSize; layOver++) {
            for (int start = 0; start < planetSize; start++) {
                for (int end = 0; end < planetSize; end++) {
                    if (start == end)
                        continue;

                    time[start][end] = Math.min(time[start][layOver] + time[layOver][end], time[start][end]);
                }
            }
        }
    }

    //출발 행성으로부터 모든 행성 방문하는 최소 시간 찾기
    public static int explorePlanets(int planetIdx, int visitedBit) {
        if (visitedBit == (1 << planetSize) - 1) {
            return 0;
        }

        if (visitedDp[planetIdx][visitedBit] != 0)
            return visitedDp[planetIdx][visitedBit];

        visitedDp[planetIdx][visitedBit] = Integer.MAX_VALUE;
        for (int next = 0; next < planetSize; next++) {
            if ((visitedBit & 1 << next) != 0)
                continue;

            visitedDp[planetIdx][visitedBit] = Math.min(explorePlanets(next, visitedBit | 1 << next) + time[planetIdx][next], visitedDp[planetIdx][visitedBit]);
        }

        return visitedDp[planetIdx][visitedBit];
    }

    public static void main(String[] args) throws IOException {
        //초기 세팅
        init();

        //모든 행성간의 최소 시간 찾기
        findMinTime();

        //출발 행성으로부터 모든 행성 방문하는 최소 시간 찾기
        minTime = explorePlanets(start, 1 << start);

        //출력
        System.out.println(minTime);
    }

    public static void init() throws IOException {
        //행성 개수, 시작 행성 위치 입력
        st = new StringTokenizer(input.readLine());
        planetSize = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken());

        //행성 간 이동 시간 입력
        time = new int[planetSize][planetSize];
        for (int row = 0; row < planetSize; row++) {
            st = new StringTokenizer(input.readLine());
            for (int col = 0; col < planetSize; col++)
                time[row][col] = Integer.parseInt(st.nextToken());
        }

        //변수 초기화
        visitedDp = new int[planetSize][1 << planetSize];
    }
}