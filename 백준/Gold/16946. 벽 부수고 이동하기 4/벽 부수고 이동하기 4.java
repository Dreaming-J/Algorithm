/*
= BOJ 16946. 벽 부수고 이동하기 4
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final char WALL = '1';

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int rowSize, colSize;
    static char[][] map;
    static int[][] countingMap;
    static Deque<Point> walls;
    static boolean[][] visited;
    static Point[][] parents;

    static class Point {
        int row, col;
        Point usedLog;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        //빈 칸인 곳이 갈 수 있는 거리 계산하기
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (visited[row][col])
                    continue;

                if (map[row][col] == WALL) {
                    walls.add(new Point(row, col));
                    continue;
                }

                count(new Point(row, col));
            }
        }

        //벽인 곳을 뚫었을 때 갈 수 있는 거리 계산하기
        while (!walls.isEmpty()) {
            Point cur = walls.poll();

            countingMap[cur.row][cur.col] = 1;

            for (int[] delta : DELTAS) {
                int nextRow = cur.row + delta[0];
                int nextCol = cur.col + delta[1];

                if (isOut(nextRow, nextCol))
                    continue;

                if (map[nextRow][nextCol] == WALL)
                    continue;

                Point parent = parents[nextRow][nextCol];

                if (parent.usedLog == cur)
                    continue;
                parent.usedLog = cur;

                countingMap[cur.row][cur.col] += countingMap[parent.row][parent.col];
            }
        }

        printAnswer();
    }

    private static void count(Point point) {
        Deque<Point> queue = new ArrayDeque<>();

        queue.add(point);
        visited[point.row][point.col] = true;

        int count = 0;
        while (!queue.isEmpty()) {
            Point cur = queue.poll();

            parents[cur.row][cur.col] = point;

            count++;

            for (int[] delta : DELTAS) {
                int nextRow = cur.row + delta[0];
                int nextCol = cur.col + delta[1];

                if (isOut(nextRow, nextCol))
                    continue;

                if (map[nextRow][nextCol] == WALL)
                    continue;

                if (visited[nextRow][nextCol])
                    continue;

                queue.add(new Point(nextRow, nextCol));
                visited[nextRow][nextCol] = true;
            }
        }

        countingMap[point.row][point.col] = count;
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }

    private static void printAnswer() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++)
                output.append(map[row][col] == WALL ? countingMap[row][col] % 10 : 0);
            output.append("\n");
        }

        System.out.println(output);
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        map = new char[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            map[row] = input.readLine().toCharArray();
        }

        countingMap = new int[rowSize][colSize];
        walls = new ArrayDeque<>();
        visited = new boolean[rowSize][colSize];
        parents = new Point[rowSize][colSize];
    }
}