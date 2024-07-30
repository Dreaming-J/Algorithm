/*
#SWEA 14413. 격자판 칠하기
NxM 크기의 격자판의 각 칸을 검은색 또는 흰색으로 칠하려고 한다.
격자판의 각 칸이 '#'이라면 검은색으로, '.'이라면 흰색으로, '?'라면 자유롭게 칠할 수 있다.
'?' 칸을 자유롭게 칠한 결과 격자판과 인접한 두 칸의 색이 항상 다르게 할 수 있는지를 판단해라

#입력
첫째 줄: 테스트 케이스의 수 T
	=각 테스트 케이스
	첫번째 줄: 격자판의 가로, 세로 크기 N, M(1<= N, M <= 50)
	두번째 줄부터 N개의 줄: '#', '.', '?'로 이루어진 격자판의 정보
#출력
테스트 케이스마다, 가능하다면 'impossible', 그렇지 않다면 'impossible'을 출력한다.

#로직
완전탐색을 이용하여 가능한지 판단한다.
0,0의 값에 따라 나오게 되는 격자판이 정해지므로 이를 이용해 문제를 푼다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final char BLACK = '#', WHITE = '.', FREE = '?', NULL = '\u0000';
    static int testCases;
    static int boardRow, boardCol;
    static char[][] board;
    static boolean possible;
    static char baseColor;

    public static void main(String args[]) throws IOException {
        testCases = Integer.parseInt(input.readLine());
        for (int test_case = 1; test_case <= testCases; test_case++) {
            //초기화
            possible = true;
            baseColor = NULL;

            //입력
            st = new StringTokenizer(input.readLine().trim());
            boardRow = Integer.parseInt(st.nextToken());
            boardCol = Integer.parseInt(st.nextToken());

            board = new char[boardRow][boardCol];
            for (int row = 0; row < boardRow; row++) {
                String line = input.readLine();
                for (int col = 0; col < boardCol; col++) {
                    char cell = line.charAt(col);
                    board[row][col] = cell;
                    if (baseColor == NULL && cell != FREE) {
                        if ((row + col) % 2 == 0)
                            baseColor = cell;
                        else {
                            if (cell == BLACK)
                                baseColor = WHITE;
                            if (cell == WHITE)
                                baseColor = BLACK;
                        }
                    }
                }
            }

            //로직
            if (baseColor != NULL) {
                for (int row = 0; row < boardRow; row++) {
                    for (int col = 0; col < boardCol; col++) {
                        if (board[row][col] == FREE)
                            continue;
                        if ((row + col) % 2 == 0 && board[row][col] != baseColor) {
                            possible = false;
                            break;
                        }
                        if ((row + col) % 2 != 0 && board[row][col] == baseColor) {
                            possible = false;
                            break;
                        }
                    }

                    if (!possible)
                        break;
                }
            }

            //출력
            System.out.printf("#%d %s\n", test_case, possible ? "possible" : "impossible");
        }
    }
}