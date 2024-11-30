/*
= BOJ 16236. 아기 상어
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    private static final int[][] DELTAS = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    private static final int INIT_SIZE = 2, SHARK = 9, EMPTY = 0;
    private static int boardSize;
    private static int[][] board;
    private static Shark shark;
    private static int answerTime;

    public static boolean findFish() {
        boolean[][] visited = new boolean[boardSize][boardSize];
        Deque<Shark> queue = new ArrayDeque<>();

        visited[shark.row][shark.col] = true;
        queue.add(shark);

        int time = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            Shark nextShark = null;

            while (size-- > 0) {
                Shark cur = queue.poll();

                if (board[cur.row][cur.col] != EMPTY && cur.size > board[cur.row][cur.col])
                    nextShark = Shark.compare(nextShark, cur);

                for (int[] delta : DELTAS) {
                    int nr = cur.row + delta[0];
                    int nc = cur.col + delta[1];

                    if (isOut(nr, nc))
                        continue;

                    if (visited[nr][nc])
                        continue;

                    if (cur.size < board[nr][nc])
                        continue;

                    visited[nr][nc] = true;
                    queue.add(new Shark(nr, nc, cur.size, cur.count));
                }
            }

            if (nextShark != null) {
                shark = nextShark;
                board[shark.row][shark.col] = EMPTY;
                shark.eat();

                answerTime += time;

                return true;
            }

            time++;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        init();

        while (findFish()) ;

        System.out.println(answerTime);
    }

    public static boolean isOut(int row, int col) {
        return row < 0 || row >= boardSize || col < 0 || col >= boardSize;
    }

    public static class Shark implements Comparable<Shark> {
        int row, col, size, count;

        public Shark(int row, int col, int size, int count) {
            this.row = row;
            this.col = col;
            this.size = size;
            this.count = count;
        }

        public void eat() {
            if (++count == size) {
                size++;
                count = 0;
            }
        }

        public static Shark compare(Shark o1, Shark o2) {
            if (o1 == null)
                return o2;

            return o1.compareTo(o2) > 0 ? o2 : o1;
        }

        @Override
        public int compareTo(Shark o) {
            return row == o.row ? col - o.col : row - o.row;
        }
    }

    public static void init() throws IOException {
        boardSize = Integer.parseInt(input.readLine());
        board = new int[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            st = new StringTokenizer(input.readLine());
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = Integer.parseInt(st.nextToken());

                if (board[row][col] == SHARK) {
                    board[row][col] = EMPTY;
                    shark = new Shark(row, col, INIT_SIZE, 0);
                }
            }
        }
    }
}
