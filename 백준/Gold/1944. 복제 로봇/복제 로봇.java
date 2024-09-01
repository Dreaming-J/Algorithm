import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int EMPTY = 0, WALL = -1;
    static int mazeSize, keySize;
    static int targetSize;
    static int[][] maze;
    static List<Point> targets;
    static List<Route>[] routes;
    static int moveCount;

    public static void findRoute() {
        for (Point target : targets) {
            int targetIdx = maze[target.row][target.col];

            boolean[][] visited = new boolean[mazeSize][mazeSize];
            Deque<Point> queue = new ArrayDeque<>();

            visited[target.row][target.col] = true;
            queue.add(target);

            while (!queue.isEmpty()) {
                Point cur = queue.poll();

                for (int[] delta : deltas) {
                    int nr = cur.row + delta[0];
                    int nc = cur.col + delta[1];

                    if (visited[nr][nc])
                        continue;

                    if (maze[nr][nc] == WALL)
                        continue;

                    if (maze[nr][nc] != EMPTY)
                        routes[targetIdx].add(new Route(maze[nr][nc], cur.distance + 1));

                    visited[nr][nc] = true;
                    queue.add(new Point(nr, nc, cur.distance + 1));
                }
            }
        }
    }

    public static void chooseRoute() {
        boolean[] visited = new boolean[targetSize + 1];

        PriorityQueue<Route> pq = new PriorityQueue<>();
        pq.add(new Route(1, 0));

        int cnt = 0;
        while (!pq.isEmpty()) {
            Route cur = pq.poll();

            if (visited[cur.num])
                continue;
            visited[cur.num] = true;

            moveCount += cur.distance;

            if (++cnt == targetSize)
                return;

            for (Route next : routes[cur.num]) {
                if (visited[next.num])
                    continue;

                pq.add(next);
            }
        }

        moveCount = -1;
    }

    public static void main(String[] args) throws IOException {
        init();

        findRoute();

        chooseRoute();

        System.out.println(moveCount);
    }

    public static class Route implements Comparable<Route> {
        int num;
        int distance;

        public Route(int num, int distance) {
            this.num = num;
            this.distance = distance;
        }

        @Override
        public int compareTo(Route o) {
            return Integer.compare(distance, o.distance);
        }
    }

    public static class Point {
        int row;
        int col;
        int distance;

        public Point(int row, int col, int distance) {
            this.row = row;
            this.col = col;
            this.distance = distance;
        }
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        mazeSize = Integer.parseInt(st.nextToken());
        keySize = Integer.parseInt(st.nextToken());

        maze = new int[mazeSize][mazeSize];
        targets = new ArrayList<>();
        targetSize = 0;
        for (int row = 0; row < mazeSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < mazeSize; col++) {
                int cell = line.charAt(col) - '0';

                if (cell != EMPTY && cell != -WALL) {
                    maze[row][col] = ++targetSize;
                    targets.add(new Point(row, col, 0));
                    continue;
                }

                maze[row][col] = -cell;
            }
        }

        routes = new ArrayList[targetSize + 1];
        for (int idx = 1; idx <= targetSize; idx++) {
            routes[idx] = new ArrayList<>();
        }
    }
}
