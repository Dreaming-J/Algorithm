/*
= BOJ 1103. 게임

- 특이사항
무한번 움직이는 것을 고려해야 한다.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final char CMD_HOLE = 'H';
    static final int HOLE = 0, NOT_VISITED = Integer.MIN_VALUE, PLAY_INFINITE = -1;
    static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int rowSize, colSize;
    static int[][] board;
    static int[][] dp;
    static boolean[][] visited;
    static int maxMove;

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(play(0, 0));
    }

    private static int play(int row, int col) {
        //범위를 벗어난 경우 게임 종료
        if (isOut(row, col))
            return 0;

        //구멍에 빠진 경우 게임 종료
        if (board[row][col] == HOLE)
            return 0;

        //이미 방문한 곳을 경로 상 중복으로 방문할 경우 사이클이 발생한 것으로 무한 번 가능해짐
        if (visited[row][col])
            return PLAY_INFINITE;

        //해당 칸을 방문한 적이 있다면 해당 값 반환
        if (dp[row][col] != NOT_VISITED)
            return dp[row][col];

        //방문 처리
        visited[row][col] = true;

        //다음 칸으로 이동
        dp[row][col] = 0;
        for (int[] delta : DELTAS) {
            int newRow = row + delta[0] * board[row][col];
            int newCol = col + delta[1] * board[row][col];

            //다음 칸에서 이동한 최대 거리
            int nextMove = play(newRow, newCol);

            //다음 칸 이동 거리가 -1이라면 무조건 -1만 반환
            if (nextMove == PLAY_INFINITE) {
                dp[row][col] = PLAY_INFINITE;
                break;
            }

            //다음 칸 이동 거리 만큼 거리 추가하기
            dp[row][col] = Math.max(nextMove + 1, dp[row][col]);
        }
        
        //방문 처리 해제
        visited[row][col] = false;
        return dp[row][col];
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        board = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++) {
                if (line.charAt(col) == CMD_HOLE)
                    continue;

                board[row][col] = line.charAt(col) - '0';
            }
        }

        dp = new int[rowSize][colSize];
        for (int[] rows : dp)
            Arrays.fill(rows, NOT_VISITED);

        visited = new boolean[rowSize][colSize];
    }
}