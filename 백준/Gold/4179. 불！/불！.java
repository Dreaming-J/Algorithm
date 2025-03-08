/*
= BOJ 4179. 불!
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final char WALL = '#', EMPTY = '.', START = 'J', FIRE = 'F';
    static final String IMPOSSIBLE = "IMPOSSIBLE";

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int rowSize, colSize;
    static char[][] maze;
    static Point start;
    static Deque<Point> firePoints;
    static boolean[][] visited;

    static class Point {
        int row, col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(escapeMaze());
    }

    public static String escapeMaze() {
        int time = 0;

        Deque<Point> queue = new ArrayDeque<>();

        visited[start.row][start.col] = true;

        queue.add(start);

        while (!queue.isEmpty()) {
            int size = queue.size();

            expandFire();

            time++;

            while (size-- > 0) {
                Point cur = queue.poll();
                int row = cur.row;
                int col = cur.col;

                for (int[] delta : DELTAS) {
                    int nr = row + delta[0];
                    int nc = col + delta[1];

                    if (isOut(nr, nc))
                        return String.valueOf(time);

                    if (maze[nr][nc] == WALL)
                        continue;

                    if (maze[nr][nc] == FIRE)
                        continue;

                    if (visited[nr][nc])
                        continue;

                    visited[nr][nc] = true;

                    queue.add(new Point(nr, nc));
                }
            }
        }

        return IMPOSSIBLE;
    }

    public static void expandFire() {
        int size = firePoints.size();

        while (size-- > 0) {
            Point cur = firePoints.poll();
            int row = cur.row;
            int col = cur.col;

            for (int[] delta : DELTAS) {
                int nr = row + delta[0];
                int nc = col + delta[1];

                if (isOut(nr, nc))
                    continue;

                if (maze[nr][nc] == WALL)
                    continue;

                if (maze[nr][nc] == FIRE)
                    continue;

                maze[nr][nc] = FIRE;
                firePoints.add(new Point(nr, nc));
            }
        }
    }

    public static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        maze = new char[rowSize][colSize];
        firePoints = new ArrayDeque<>();
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++) {
                maze[row][col] = line.charAt(col);

                if (maze[row][col] == START) {
                    start = new Point(row, col);

                    maze[row][col] = EMPTY;
                }

                if (maze[row][col] == FIRE) {
                    firePoints.add(new Point(row, col));
                }
            }
        }

        visited = new boolean[rowSize][colSize];
    }
}