/*
= BOJ 11967. 불켜기
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int mapSize, buttonSize;
    static ArrayList<Integer>[][] map;
    static boolean[][] isOn, visited;
    static int roomSize;
    static Deque<Integer> queue;

    public static void findBrightRooms() {
        queue = new ArrayDeque<>();

        isOn[0][0] = true;
        visited[0][0] = true;
        queue.add(0);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int row = cur / mapSize;
            int col = cur % mapSize;


            if (map[row][col] != null) {
                for (int button : map[row][col])
                    turnOn(button);
            }

            for (int[] delta : DELTA) {
                int nr = cur / mapSize + delta[0];
                int nc = cur % mapSize + delta[1];

                if (isOut(nr, nc))
                    continue;

                if (visited[nr][nc])
                    continue;

                if (!isOn[nr][nc])
                    continue;

                visited[nr][nc] = true;
                queue.add(nr * mapSize + nc);
            }
        }
    }

    public static void turnOn(int button) {
        int brow = button / mapSize;
        int bcol = button % mapSize;

        if (isOn[brow][bcol])
            return;
        isOn[brow][bcol] = true;
        roomSize++;

        for (int[] delta : DELTA) {
            int nr = brow + delta[0];
            int nc = bcol + delta[1];

            if (isOut(nr, nc))
                continue;

            if (visited[nr][nc]) {
                visited[brow][bcol] = true;
                queue.add(button);
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findBrightRooms();

        System.out.println(roomSize);
    }

    public static boolean isOut(int row, int col) {
        return row < 0 || row >= mapSize || col < 0 || col >= mapSize;
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        mapSize = Integer.parseInt(st.nextToken());
        buttonSize = Integer.parseInt(st.nextToken());

        map = new ArrayList[mapSize][mapSize];
        for (int idx = 0; idx < buttonSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            int to = (Integer.parseInt(st.nextToken()) - 1) * mapSize + Integer.parseInt(st.nextToken()) - 1;

            if (map[row][col] == null)
                map[row][col] = new ArrayList();

            map[row][col].add(to);
        }

        isOn = new boolean[mapSize][mapSize];
        visited = new boolean[mapSize][mapSize];
        roomSize = 1;
    }
}