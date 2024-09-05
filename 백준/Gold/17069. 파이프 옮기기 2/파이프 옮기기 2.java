/*
= 백준 17069. 파이프 옮기기 2

1. 초기 세팅
    1-1. 집의 크기 입력
    1-2. 집의 크기만큼 집의 상태 입력
    1-3. 변수 초기화
2. 모든 칸으로 이동할 수 있는 경우의 수 찾기
    2-1. 시작 위치 초기화
    2-2. 갈 수 있는 경로 찾기
        2-2-1. 가로 이동
        2-2-2. 대각선 이동
        2-2-3. 세로 이동
3. 목표 지점으로 이동시키는 경우의 수 계산
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int HORIZONTAL = 0, DIAGONAL = 1, VERTICAL = 2, DIRECTION_SIZE = 3;
    static final int EMPTY = 0, WALL = 1;
    static int homeSize;
    static int[][] home;
    static long[][][] moveCount;

    //2. 모든 칸으로 이동할 수 있는 경우의 수 찾기
    public static void findMoveCount() {
        //2-1. 시작 위치 초기화
        moveCount[HORIZONTAL][1][2] = 1;

        //2-2. 갈 수 있는 경로 찾기
        for (int row = 1; row <= homeSize; row++) {
            for (int col = 3; col <= homeSize; col++) {
                if (home[row][col] == WALL)
                    continue;

                //2-2-1. 가로 이동
                moveCount[HORIZONTAL][row][col] =
                        moveCount[HORIZONTAL][row][col - 1] + moveCount[DIAGONAL][row][col - 1];

                //2-2-2. 대각선 이동
                if (home[row - 1][col] == EMPTY && home[row][col - 1] == EMPTY)
                    moveCount[DIAGONAL][row][col] =
                            moveCount[HORIZONTAL][row - 1][col - 1] + moveCount[DIAGONAL][row - 1][col - 1]
                                    + moveCount[VERTICAL][row - 1][col - 1];

                //2-2-3. 세로 이동
                moveCount[VERTICAL][row][col] =
                        moveCount[DIAGONAL][row - 1][col] + moveCount[VERTICAL][row - 1][col];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 모든 칸으로 이동할 수 있는 경우의 수 찾기
        findMoveCount();

        //3. 목표 지점으로 이동시키는 경우의 수 계산
        long count = 0;
        for (int idx = 0; idx < DIRECTION_SIZE; idx++) {
            count += moveCount[idx][homeSize][homeSize];
        }

        //4. 출력
        System.out.println(count);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 집의 크기 입력
        homeSize = Integer.parseInt(input.readLine());

        //1-2. 집의 크기만큼 집의 상태 입력
        home = new int[homeSize + 1][homeSize + 1];
        for (int row = 1; row <= homeSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 1; col <= homeSize; col++) {
                home[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        //1-3. 변수 초기화
        moveCount = new long[DIRECTION_SIZE][homeSize + 1][homeSize + 1];
    }
}
