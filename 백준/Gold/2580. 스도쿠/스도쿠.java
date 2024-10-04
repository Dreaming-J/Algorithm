/*
= BOJ 2580. 스도쿠
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int BOARD_SIZE = 9, NUMBER_SIZE = 9, BOX_SIZE = 3, NONE = 0;
    static int[][] board;
    static int[] verticalUsedBit, horizontalUsedBit, boxUsedBit;
    static boolean isFind;

    //2. 스도쿠 판 채우기
    public static void sudoku(int position) {
        if (isFind)
            return;

        int row = position / BOARD_SIZE;
        int col = position % BOARD_SIZE;

        if (position == BOARD_SIZE * BOARD_SIZE) {
            for (int curRow = 0; curRow < BOARD_SIZE; curRow++) {
                for (int curCol = 0; curCol < BOARD_SIZE; curCol++) {
                    output.append(board[curRow][curCol]).append(" ");
                }
                output.append("\n");
            }
            isFind = true;

            return;
        }

        if (board[row][col] != NONE) {
            sudoku(position + 1);
            return;
        }

        for (int num = 1; num <= NUMBER_SIZE; num++) {
            if ((horizontalUsedBit[row] & 1 << num) != 0)
                continue;

            if ((verticalUsedBit[col] & 1 << num) != 0)
                continue;

            if ((boxUsedBit[(row / BOX_SIZE) * BOX_SIZE + col / BOX_SIZE] & 1 << num) != 0)
                continue;

            board[row][col] = num;
            horizontalUsedBit[row] |= 1 << num;
            verticalUsedBit[col] |= 1 << num;
            boxUsedBit[(row / BOX_SIZE) * BOX_SIZE + col / BOX_SIZE] |= 1 << num;

            sudoku(position + 1);

            board[row][col] = NONE;
            horizontalUsedBit[row] &= ~(1 << num);
            verticalUsedBit[col] &= ~(1 << num);
            boxUsedBit[(row / BOX_SIZE) * BOX_SIZE + col / BOX_SIZE] &= ~(1 << num);
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 스도쿠 판 채우기
        sudoku(0);

        //3. 출력
        System.out.println(output);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 변수 초기화
        board = new int[BOARD_SIZE][BOARD_SIZE];
        horizontalUsedBit = new int[NUMBER_SIZE];
        verticalUsedBit = new int[NUMBER_SIZE];
        boxUsedBit = new int[NUMBER_SIZE];

        //1-2. 보드의 정보 입력
        for (int row = 0; row < BOARD_SIZE; row++) {
            st = new StringTokenizer(input.readLine());
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = Integer.parseInt(st.nextToken());

                if (board[row][col] != NONE) {
                    horizontalUsedBit[row] |= 1 << board[row][col];
                    verticalUsedBit[col] |= 1 << board[row][col];
                    boxUsedBit[(row / BOX_SIZE) * BOX_SIZE + col / BOX_SIZE] |= 1 << board[row][col];
                }
            }
        }
    }
}