/*
= BOJ 1035. 조각 움직이기

- 특이사항
보드는 5x5 크기, 조각은 최대 5개로 고정이므로, 완전 탐색 가능
5x5 크기이므로 보드판을 비트로 보관이 가능하므로, 보드판을 가지고 bfs를 돌아도 부담이 없음.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int BOARD_SIZE = 5, MAX_PIECES_SIZE = 5;
    static final char PIECE = '*';
    static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int pieceSize;
    static State state;

    static class State {
        int board;
        int[] pieces;

        public State(int board, int[] pieces) {
            this.board = board;
            this.pieces = pieces;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(findMinMoveCount());
    }
    private static int findMinMoveCount() {
        Deque<State> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(state);
        visited.add(state.board);

        int time = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                State cur = queue.poll();

                //모든 조각이 다 붙었다면 탐색 종료
                if (isConnectedAllPieces(cur.board, cur.pieces[0]))
                    return time;

                //조각 이동
                for (int pieceIdx = 0; pieceIdx < pieceSize; pieceIdx++) {
                    for (int[] delta : DELTAS) {
                        int nextRow = cur.pieces[pieceIdx] / BOARD_SIZE + delta[0];
                        int nextCol = cur.pieces[pieceIdx] % BOARD_SIZE + delta[1];
                        int nextIdx = getCoordinateToIndex(nextRow, nextCol);

                        if (isOut(nextRow, nextCol))
                            continue;

                        if ((cur.board & 1 << nextIdx) != 0)
                            continue;

                        State nextState = getNextState(cur, pieceIdx, nextIdx);

                        if (visited.contains(nextState.board))
                            continue;

                        queue.add(nextState);
                        visited.add(nextState.board);
                    }
                }
            }
            time++;
        }

        return -1;
    }

    private static boolean isConnectedAllPieces(int board, int piece) {
        Deque<Integer> queue = new ArrayDeque<>();

        queue.add(piece);
        board &= ~(1 << piece);

        int connectedCount = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();

            connectedCount++;

            for (int[] delta : DELTAS) {
                int nextRow = cur / BOARD_SIZE + delta[0];
                int nextCol = cur % BOARD_SIZE + delta[1];
                int next = getCoordinateToIndex(nextRow, nextCol);

                if (isOut(nextRow, nextCol))
                    continue;

                if ((board & 1 << next) == 0)
                    continue;

                queue.add(next);
                board &= ~(1 << next);
            }
        }

        return connectedCount == pieceSize;
    }

    private static State getNextState(State cur, int pieceIdx, int nextIdx) {
        int nextBoard = cur.board;

        nextBoard &= ~(1 << cur.pieces[pieceIdx]);
        nextBoard |= 1 << nextIdx;

        int[] nextPieces = Arrays.copyOf(cur.pieces, pieceSize);
        nextPieces[pieceIdx] = nextIdx;

        return new State(nextBoard, nextPieces);
    }

    private static void init() throws IOException {
        int board = 0;
        int[] pieces = new int[MAX_PIECES_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            String line = input.readLine();
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (line.charAt(col) == PIECE) {
                    int idx = getCoordinateToIndex(row, col);
                    board |= 1 << idx;
                    pieces[pieceSize++] = idx;
                }
            }
        }

        state = new State(board, Arrays.copyOf(pieces, pieceSize));
    }

    private static int getCoordinateToIndex(int row, int col) {
        return row * BOARD_SIZE + col;
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE;
    }
}