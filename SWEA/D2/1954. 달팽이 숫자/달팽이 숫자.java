/*
#SWEA 1954. 달팽이 숫자
1부터 N*N까지의 숫자가 시계방향으로 이루어져 있을 때, N크기의 달팽이를 출력해라

#입력
첫째 줄: 테스트 케이스의 수 T
    =각 테스트 케이스
    첫번째 줄: 달팽이의 크기 N (1 <= N <= 10)
#출력
#Ti
여기부터 달팽이 출력

#로직
0,0부터 오른쪽 방향부터 시작하여 이동하다 벽 or 이미 값이 쓰여진 칸을 만나면 시계 방향으로 방향 전환
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Solution {

    public static void main(String args[]) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        final int[][] deltas = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; //오른쪽부터 시계 방향 델타 배열
        final int EMPTY = 0;
        int testCases;
        int boardSize;
        int[][] board;
        int curRow, curCol, dir;

        testCases = Integer.parseInt(input.readLine());
        for (int testCase = 1; testCase <= testCases; testCase++) {
            //입력 및 초기화
            boardSize = Integer.parseInt(input.readLine());
            board = new int[boardSize][boardSize];
            curRow = 0;
            curCol = 0;
            dir = 0;

            //로직
            for (int num = 1; num <= boardSize * boardSize; num++) {
                board[curRow][curCol] = num;

                int nr = curRow + deltas[dir][0];
                int nc = curCol + deltas[dir][1];
                if (cantGo(nr, nc, boardSize) || board[nr][nc] != EMPTY)
                    dir = (dir + 1) % deltas.length;

                curRow += deltas[dir][0];
                curCol += deltas[dir][1];
            }

            //출력
            output.append(String.format("#%d\n", testCase));
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    output.append(String.valueOf(board[row][col] + " "));
                }
                output.append("\n");
            }
        }

        System.out.println(output.toString());
    }

    private static boolean cantGo(int row, int col, int boardSize) {
        return row < 0 || row >= boardSize || col < 0 || col >= boardSize;
    }
}