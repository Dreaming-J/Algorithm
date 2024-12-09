/*
= BOJ 4991. 로봇 청소기
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringJoiner output = new StringJoiner("\n");
    static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final String END_SIGNAL = "0 0";
    static final char EMPTY = '.', DIRTY_CELL = '*', FURNITURE = 'x', ROBOVAC = 'o';
    static final char LOWER_DIRTY_CELL = '0', UPPER_DIRTY_CELL = '9';
    static int colSize, rowSize;
    static char[][] room;
    static int dirtyCellSize;
    static Point startRobovac;
    static boolean[][][] visited;

    public static class Point {
        int row, col, cleaningBit;

        public Point(int row, int col, int cleaningBit) {
            this.row = row;
            this.col = col;
            this.cleaningBit = cleaningBit;
        }
    }

    public static int cleanRoom() {
        Deque<Point> queue = new ArrayDeque<>();

        visited[startRobovac.row][startRobovac.col][startRobovac.cleaningBit] = true;
        queue.add(startRobovac);

        int time = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                Point cur = queue.poll();

                if (cur.cleaningBit == (1 << dirtyCellSize) - 1)
                    return time;

                for (int[] delta : DELTAS) {
                    int nr = cur.row + delta[0];
                    int nc = cur.col + delta[1];

                    if (isOut(nr, nc))
                        continue;

                    if (visited[nr][nc][cur.cleaningBit])
                        continue;

                    if (room[nr][nc] == FURNITURE)
                        continue;

                    int nextCleaningBit = cur.cleaningBit;
                    if (room[nr][nc] >= LOWER_DIRTY_CELL && room[nr][nc] <= UPPER_DIRTY_CELL)
                        nextCleaningBit |= 1 << (room[nr][nc] - '0');

                    visited[nr][nc][nextCleaningBit] = true;
                    queue.add(new Point(nr, nc, nextCleaningBit));
                }
            }

            time++;
        }

        return -1;
    }

    public static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }

    public static void main(String[] args) throws IOException {
        while (init())
            output.add(String.valueOf(cleanRoom()));

        System.out.println(output);
    }

    public static boolean init() throws IOException {
        String roomInfo = input.readLine();
        if (roomInfo.equals(END_SIGNAL))
            return false;

        st = new StringTokenizer(roomInfo);
        colSize = Integer.parseInt(st.nextToken());
        rowSize = Integer.parseInt(st.nextToken());

        dirtyCellSize = 0;
        room = new char[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++) {
                room[row][col] = line.charAt(col);

                if (room[row][col] == ROBOVAC) {
                    startRobovac = new Point(row, col, 0);
                    room[row][col] = EMPTY;
                }

                if (room[row][col] == DIRTY_CELL)
                    room[row][col] = (char) (dirtyCellSize++ + '0');
            }
        }

        visited = new boolean[rowSize][colSize][1 << dirtyCellSize];

        return true;
    }
}
