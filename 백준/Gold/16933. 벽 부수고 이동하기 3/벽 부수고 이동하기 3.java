/*
= BOJ 16933. 벽 부수고 이동하기 3

- 특이사항
홀수 시간일 때 낮, 짝수 시간일 때 밤
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int WALL = 1;
    static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int rowSize, colSize, powerSize;
    static int[][] map;

    static class Point {
        int row, col, power;
        boolean stay;

        public Point(int row, int col, int power, boolean stay) {
            this.row = row;
            this.col = col;
            this.power = power;
            this.stay = stay;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(getShortestPath());
    }

    private static int getShortestPath() {
        Deque<Point> queue = new ArrayDeque<>();
        boolean[][][] visited = new boolean[rowSize][colSize][powerSize + 1];

        queue.add(new Point(0, 0, powerSize, false));
        visited[0][0][powerSize] = true;

        int time = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                Point cur = queue.poll();

                if (cur.row == rowSize - 1 && cur.col == colSize - 1)
                    return time;

                if (!cur.stay && isNight(time))
                    queue.add(new Point(cur.row, cur.col, cur.power, true));

                for (int[] delta : DELTAS) {
                    int nextRow = cur.row + delta[0];
                    int nextCol = cur.col + delta[1];
                    int nextPower = cur.power;

                    if (isOut(nextRow, nextCol))
                        continue;

                    if (map[nextRow][nextCol] == WALL) {
                        if (cur.power == 0 || isNight(time))
                            continue;

                        nextPower--;
                    }

                    if (visited[nextRow][nextCol][nextPower])
                        continue;

                    queue.add(new Point(nextRow, nextCol, nextPower, false));
                    visited[nextRow][nextCol][nextPower] = true;
                }
            }
            time++;
        }

        return -1;
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        powerSize = Integer.parseInt(st.nextToken());

        map = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++)
                map[row][col] = line.charAt(col) - '0';
        }
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }

    private static boolean isNight(int time) {
        return time % 2 == 0;
    }
}