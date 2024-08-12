/*
#SWEA 1210. [S/W 문제해결 기본] 2일차 - Ladder1
사다리타기용 보드가 주어질 때, 지정된 도착점에 대응되는 출발점 X를 구해라.
주어진 보드에 연속으로 이어지는 가로선은 존재하지 않는다.

#입력
첫째 줄: 테스트 케이스의 수 T
    =각 테스트 케이스
    첫번째 줄: 테스트 케이스 번호
    두번째 줄부터 100개의 줄: 사다리타기용 보드의 정보
        1 = 사다리, 2 = 도착 지점
#출력
#Ti 출발점의 x좌표

#로직
도착점으로부터 시작점을 역순으로 구한다.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Solution {
    static final int BOARD_SIZE = 100;

    public static void main(String args[]) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        final int LADDER = 1, DESTINATION = 2;
        final int[] deltas = {-1, 1}; //좌우 확인을 위한 델타 배열
        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
        int[] curPoint = new int[2];

        for (int testCase = 1; testCase <= 10; testCase++) {
            //입력 및 초기화
            input.readLine(); //테스트 케이스 번호 입력은 필요 없음
            for (int row = 0; row < BOARD_SIZE; row++) {
                st = new StringTokenizer(input.readLine().trim());
                for (int col = 0; col < BOARD_SIZE; col++) {
                    int cell = Integer.parseInt(st.nextToken());
                    board[row][col] = cell;
                    if (cell == DESTINATION) {
                        curPoint[0] = row;
                        curPoint[1] = col;
                    }
                }
            }

            //로직
            while (curPoint[0] > 0) {
                //좌우에 사다리 있는 지 확인
                for (int delta : deltas) {
                    boolean isGo = false;
                    while (canGo(curPoint[1] + delta) && board[curPoint[0]][curPoint[1] + delta] == LADDER) {
                        isGo = true;
                        curPoint[1] += delta;
                    }

                    if (isGo)
                        break;
                }

                curPoint[0]--;
            }

            //출력
            output.write(String.format("#%d %d\n", testCase, curPoint[1]));
        }

        output.flush();
        output.close();
    }

    public static boolean canGo(int col) {
        return col >= 0 && col < BOARD_SIZE;
    }
}