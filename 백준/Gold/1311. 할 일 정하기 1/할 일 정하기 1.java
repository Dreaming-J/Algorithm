/*
= BOJ 1311. 할 일 정하기 1

= 특이사항
모든 사람은 모든 일을 할 능력이 있다
    -> dpCost를 통해 가지 못하는 경로가 생기는 일은 없다.
    -> 특정 경로를 탐색하면 무조건 초기에 설정한 값에서 변한다.
    -> INF로 초기화하고 이를 기준으로 방문 여부를 판단해도 괜찮다.

= 로직
1. 초기 세팅
    1-1. 사람과 일의 수 입력
    1-2. 일의 비용 입력
    1-3. 변수 초기화
2. 최소 비용 계산
    2-1. 모든 일을 완료했다면 0 반환
    2-2. 처리하지 않은 일의 최소 비용을 이미 알고 있다면, 해당 값 반환
    2-3. 현재 상태에서 남은 일의 모든 경로를 최적화 후, 찾은 값 반환
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
    static final int INF = 200_000;
    static int size, minCost;
    static int[][] cost, dpCost;

    public static int findMinCost(int personIdx, int workBit) {
        //2-1. 모든 일을 완료했다면 0 반환
        if (workBit == (1 << size) - 1)
            return 0;

        //2-2. 처리하지 않은 일의 최소 비용을 이미 알고 있다면, 해당 값 반환
        if (dpCost[personIdx][workBit] != INF)
            return dpCost[personIdx][workBit];

        //2-3. 현재 상태에서 남은 일의 모든 경로를 최적화 후, 찾은 값 반환
        for (int workIdx = 0; workIdx < size; workIdx++) {
            //이미 처리한 일이라면 패스
            if ((workBit & 1 << workIdx) != 0)
                continue;

            //원래의 값 vs 현재 사람이 해당 일을 하는 비용 + 남은 일을 나머지 사람들이 처리하는 최적 비용
            dpCost[personIdx][workBit] = Math.min(
                    findMinCost(personIdx + 1, workBit | 1 << workIdx) + cost[personIdx][workIdx],
                    dpCost[personIdx][workBit]);
        }

        return dpCost[personIdx][workBit];
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 최소 비용 계산
        minCost = findMinCost(0, 0);

        //3. 출력
        System.out.println(minCost);
    }

    public static void init() throws IOException {
        //1-1. 사람과 일의 수 입력
        size = Integer.parseInt(input.readLine());

        //1-2. 일의 비용 입력
        cost = new int[size][size];
        for (int personIdx = 0; personIdx < size; personIdx++) {
            st = new StringTokenizer(input.readLine());
            for (int workIdx = 0; workIdx < size; workIdx++) {
                cost[personIdx][workIdx] = Integer.parseInt(st.nextToken());
            }
        }

        //1-3. 변수 초기화
        dpCost = new int[size][1 << size];
        for (int[] array : dpCost) {
            Arrays.fill(array, INF);
        }
    }
}
