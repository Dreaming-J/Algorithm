/*
= BOJ 2206. 벽 부수고 이동하기
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final char WALL = '1';
    static final int BROKE_POWER = 10_000_000, EXIST = 1;
    static int rowSize, colSize;
    static char[][] map;
    static int minDist;

    public static void findMinDist() {
        boolean[][] visited = new boolean[2][rowSize * colSize];
        Deque<Integer> queue = new ArrayDeque<>();

        visited[EXIST][0] = true;
        queue.add(BROKE_POWER);

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                int cur = queue.poll();
                int power = cur / BROKE_POWER;
                cur %= BROKE_POWER;
                int row = cur / colSize;
                int col = cur % colSize;

                if (cur == rowSize * colSize - 1)
                    return;

                for (int[] delta : DELTA) {
                    int nr = row + delta[0];
                    int nc = col + delta[1];
                    int next = nr * colSize + nc;

                    if (isOut(nr, nc))
                        continue;

                    if (visited[power][next])
                        continue;

                    if (map[nr][nc] == WALL) {
                        if (power == EXIST) {
                            visited[power][next] = true;
                            queue.add(next);
                        }
                    } else {
                        visited[power][next] = true;
                        queue.add(next + power * BROKE_POWER);
                    }
                }
            }

            minDist++;
        }

        minDist = -1;
    }

    public static void main(String[] args) throws IOException {
        init();

        findMinDist();

        System.out.println(minDist);
    }

    public static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        map = new char[rowSize][colSize];
        for (int row = 0; row < rowSize; row++)
            map[row] = input.readLine().toCharArray();

        minDist = 1;
    }
}