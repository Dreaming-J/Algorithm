/*
= 백준 17070. 파이프 옮기기 1

1. 집의 크기 입력
2. 집의 크기만큼 집의 상태 입력 (빈 칸 = 0, 벽 = 1)
3. 변수 초기화
4. (0, 1)부터 시작하여, (N - 1, N - 1)에 도달할 수 있는지 확인
    4-1. 종료 조건
        4-1-1. 해당 칸이 범위를 벗어났다면 탐색 종료
        4-1-2. 이동 방향에 따른 범위에 맞춰 주변에 벽이 있다면 탐색 종료
        4-1-3. 목표 지점에 도달했다면 방법의 개수를 증가시킨 후 탐색 종료
    4-2. 현재 방향에 맞춰 진행할 수 있는 방향으로 탐색
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int HORIZONTAL = 0, DIAGONAL = 1, VERTICAL = 2;
    static final int[][][] deltas = {
            {{0, 1, HORIZONTAL}, {1, 1, DIAGONAL}},
            {{1, 0, VERTICAL}, {1, 1, DIAGONAL}, {0, 1, HORIZONTAL}},
            {{1, 0, VERTICAL}, {1, 1, DIAGONAL}}}; //int[현재 방향][이동 방향][행, 열, 이동 방향]
    static final int EMPTY = 0, WALL = 1;
    static int homeSize;
    static int[][] home;
    static int pipeMoveToEndCount;

    public static void init() throws IOException {
        //1. 집의 크기 입력
        homeSize = Integer.parseInt(input.readLine());

        //2. 집의 크기만큼 집의 상태 입력 (빈 칸 = 0, 벽 = 1)
        home = new int[homeSize][homeSize];
        for (int row = 0; row < homeSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < homeSize; col++) {
                home[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        //3. 변수 초기화
        pipeMoveToEndCount = 0;
    }

    public static void findPipeMoveRoute(int row, int col, int direction) {
        //4-1. 종료 조건
        //4-1-1. 해당 칸이 범위를 벗어났다면 탐색 종료
        if (!canGo(row, col))
            return;

        //4-1-2. 이동 방향에 따른 범위에 맞춰 주변에 벽이 있다면 탐색 종료
        if (home[row][col] == WALL) //자신이 위치한 공간이 벽이면 탐색 종료
            return;
        if (direction == DIAGONAL) { //대각선 상태라면, 주변 공간도 벽인지 확인
            if (home[row - 1][col] == WALL || home[row][col - 1] == WALL)
                return;
        }

        //4-1-3. 목표 지점에 도달했다면 방법의 개수를 증가시킨 후 탐색 종료
        if (row == homeSize - 1 && col == homeSize - 1) {
            pipeMoveToEndCount++;
            return;
        }

        //4-2. 현재 방향에 맞춰 진행할 수 있는 방향으로 탐색
        for (int[] delta : deltas[direction]) {
            findPipeMoveRoute(row + delta[0], col + delta[1], delta[2]);
        }
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < homeSize && col >= 0 && col < homeSize;
    }

    public static void main(String[] args) throws IOException {
        init();

        //4. (0, 1)부터 시작하여, (N - 1, N - 1)에 도달할 수 있는지 확인
        findPipeMoveRoute(0, 1, HORIZONTAL);

        System.out.println(pipeMoveToEndCount);
    }
}
