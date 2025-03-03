/*
= BOJ 2589. 보물섬
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    static final int[][] DELTAS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final char WATER = 'W';

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int rowSize, colSize;
    static char[][] map;
    static int[][] visited;
    static int answer;

    public static void main(String[] args) throws IOException {
        init();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (map[row][col] == WATER)
                    continue;

                answer = Math.max(findMaxTime(row, col), answer);
            }
        }

        System.out.println(answer);
    }

    public static int findMaxTime(int startRow, int startCol) {
        int time = -1;
        int startPos = startRow * colSize + startCol;

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(startPos);
        visited[startRow][startCol] = startPos;

        while (!queue.isEmpty()) {
            int size = queue.size();

            time++;

            while (size-- > 0) {
                int pos = queue.poll();
                int row = pos / colSize;
                int col = pos % colSize;

                for (int[] delta : DELTAS) {
                    int nr = row + delta[0];
                    int nc = col + delta[1];

                    if (isOut(nr, nc))
                        continue;

                    if (visited[nr][nc] == startPos)
                        continue;

                    if (map[nr][nc] == WATER)
                        continue;

                    queue.add(nr * colSize + nc);
                    visited[nr][nc] = startPos;
                }
            }
        }

        return time;
    }

    public static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        map = new char[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++) {
                map[row][col] = line.charAt(col);
            }
        }

        visited = new int[rowSize][colSize];
    }
}