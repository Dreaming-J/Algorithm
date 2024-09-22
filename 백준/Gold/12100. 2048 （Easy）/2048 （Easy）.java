/*
= BOJ 12100. 2048 (Easy)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    static final int MOVE_SIZE = 4, MAX_MOVE_COUNT = 5;
    static final int EMPTY = 0;
    static Map<Integer, Runnable> moveMethods;
    static int boardSize;
    static int[][] board;
    static boolean[][] usedCell;
    static int maxBlock;

    public static void play(int moveCount) {
        if (moveCount == MAX_MOVE_COUNT) {
            maxBlock = Math.max(findMaxBlock(), maxBlock);
            return;
        }

        int[][] savedBoard = new int[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++)
            savedBoard[row] = Arrays.copyOf(board[row], boardSize);

        for (int moveIdx = 0; moveIdx < MOVE_SIZE; moveIdx++) {
            initUsedCell();
            moveMethods.get(moveIdx).run();

            play(moveCount + 1);

            for (int row = 0; row < boardSize; row++)
                board[row] = Arrays.copyOf(savedBoard[row], boardSize);
        }
    }

    //최대 블록 값 찾기
    public static int findMaxBlock() {
        int maxBlock = 0;

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                maxBlock = Math.max(board[row][col], maxBlock);
            }
        }

        return maxBlock;
    }

    //위쪽으로 블록 이동
    public static void moveUp() {
        for (int col = 0; col < boardSize; col++) {
            for (int row = 1; row < boardSize; row++) {
                if (usedCell[row][col])
                    continue;

                if (board[row][col] == EMPTY)
                    continue;

                moveCell(DELTA[UP], row, col);
            }
        }
    }

    //아래쪽으로 블록 이동
    public static void moveDown() {
        for (int col = 0; col < boardSize; col++) {
            for (int row = boardSize - 2; row >= 0; row--) {
                if (usedCell[row][col])
                    continue;

                if (board[row][col] == EMPTY)
                    continue;

                moveCell(DELTA[DOWN], row, col);
            }
        }
    }

    //왼쪽으로 블록 이동
    public static void moveLeft() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 1; col < boardSize; col++) {
                if (usedCell[row][col])
                    continue;

                if (board[row][col] == EMPTY)
                    continue;

                moveCell(DELTA[LEFT], row, col);
            }
        }
    }

    //오른쪽으로 블록 이동
    public static void moveRight() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = boardSize - 2; col >= 0; col--) {
                if (usedCell[row][col])
                    continue;

                if (board[row][col] == EMPTY)
                    continue;

                moveCell(DELTA[RIGHT], row, col);
            }
        }
    }

    //해당 칸 블록을 주어진 방향으로 가능한 만큼 이동 및 결합
    public static void moveCell(int[] delta, int row, int col) {
        int value = board[row][col];
        board[row][col] = EMPTY;

        while (true) {
            int nr = row + delta[0];
            int nc = col + delta[1];

            //다음 칸이 범위를 벗어났다면
            if (!canGo(nr, nc)) {
                board[row][col] = value;
                return;
            }

            //다음 칸이 사용된 칸이라면
            if (usedCell[nr][nc]) {
                board[row][col] = value;
                return;
            }

            //다음 칸이 비어있지 않고 값이 다르다면
            if (board[nr][nc] != EMPTY && board[nr][nc] != value) {
                board[row][col] = value;
                return;
            }

            //다음 칸이 같다면
            if (board[nr][nc] == value) {
                board[nr][nc] *= 2;
                usedCell[nr][nc] = true;
                return;
            }

            row = nr;
            col = nc;
        }
    }

    public static void main(String[] args) throws IOException {
        //초기 세팅
        init();

        //2048게임 시뮬레이션
        play(0);

        System.out.println(maxBlock);
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }

    public static void initUsedCell() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                usedCell[row][col] = false;
            }
        }
    }

    public static void init() throws IOException {
        //보드 크기 입력
        boardSize = Integer.parseInt(input.readLine());

        //보드 정보 입력
        board = new int[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            st = new StringTokenizer(input.readLine());
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        //변수 초기화
        usedCell = new boolean[boardSize][boardSize];

        //함수 실행용 map 초기화
        moveMethods = new HashMap<>();
        moveMethods.put(UP, Main::moveUp);
        moveMethods.put(DOWN, Main::moveDown);
        moveMethods.put(LEFT, Main::moveLeft);
        moveMethods.put(RIGHT, Main::moveRight);
    }
}