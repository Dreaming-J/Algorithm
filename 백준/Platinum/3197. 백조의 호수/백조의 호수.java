/*
= BOJ 3197. 백조의 호수

= 로직
1. 초기 세팅
    1-1. 호수의 행과 열 입력
    1-2. 호수의 행만큼 호수 정보 입력
    1-3. 변수 초기화
2. 물 덩어리의 집합을 구한다.
2. 시간이 지남에 따라 빙판을 녹인다.
    2-1. 녹은 빙판의 좌표는
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int SWAN_SIZE = 2;
    static final char WATER = '.', ICE = 'X', SWAN = 'L';

    //union-find 알고리즘을 위한 변수
    static Point[][] parents;
    static int[][] rank;

    static int rowSize, colSize;
    static char[][] lake;
    static Point[] swans;
    static boolean[][] visited;
    static Deque<Point> meltingIce;
    static int time;

    public static void findPuddle(Point point) {
        Deque<Point> queue = new ArrayDeque<>();

        visited[point.row][point.col] = true;
        queue.add(point);

        while (!queue.isEmpty()) {
            Point cur = queue.poll();

            if (lake[cur.row][cur.col] == ICE) {
                meltingIce.add(cur);
                continue;
            }

            union(point, cur);

            for (int[] delta : deltas) {
                int nr = cur.row + delta[0];
                int nc = cur.col + delta[1];

                if (!canGo(nr, nc))
                    continue;

                if (visited[nr][nc])
                    continue;

                visited[nr][nc] = true;
                queue.add(new Point(nr, nc));
            }
        }
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }

    public static void melt() {
        int size = meltingIce.size();

        while (size-- > 0) {
            Point cur = meltingIce.poll();

            if (lake[cur.row][cur.col] != ICE)
                continue;
            lake[cur.row][cur.col] = WATER;

            for (int[] delta : deltas) {
                int nr = cur.row + delta[0];
                int nc = cur.col + delta[1];

                if (!canGo(nr, nc))
                    continue;

                if (lake[nr][nc] != ICE) {
                    union(cur, new Point(nr, nc));
                    continue;
                }

                meltingIce.add(new Point(nr, nc));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 물 덩어리의 집합을 구한다.
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (visited[row][col])
                    continue;

                if (lake[row][col] == ICE)
                    continue;

                findPuddle(new Point(row, col));
            }
        }

        //3. 매 시간마다 물과 닿은 얼음을 녹인다.
        while (!Objects.equals(find(swans[0]), find(swans[1]))) {
            time++;
            melt();
        }

        System.out.println(time);
    }

    public static class Point {
        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (o.getClass() != Point.class)
                return false;

            Point other = (Point) o;

            return this.row == other.row && this.col == other.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    public static boolean union(Point point1, Point point2) {
        Point parent1 = find(point1);
        Point parent2 = find(point2);

        if (Objects.equals(parent1, parent2))
            return false;

        if (rank[parent1.row][parent1.col] > rank[parent2.row][parent2.col]) {
            parents[parent2.row][parent2.col] = parent1;
            return true;
        }

        if (rank[parent1.row][parent1.col] == rank[parent2.row][parent2.col])
            rank[parent2.row][parent2.col]++;

        parents[parent1.row][parent1.col] = parent2;
        return true;
    }

    public static Point find(Point point) {
        if (parents[point.row][point.col] == null) {
            return point;
        }

        return parents[point.row][point.col] = find(parents[point.row][point.col]);
    }

    public static void init() throws IOException {
        //1-1. 호수의 행과 열 입력
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        //1-2. 호수의 행만큼 호수 정보 입력
        lake = new char[rowSize][colSize];
        swans = new Point[SWAN_SIZE];
        int swanIdx = 0;
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++) {
                lake[row][col] = line.charAt(col);

                if (lake[row][col] == SWAN)
                    swans[swanIdx++] = new Point(row, col);
            }
        }

        //1-3. 변수 초기화
        parents = new Point[rowSize][colSize];
        rank = new int[rowSize][colSize];
        visited = new boolean[rowSize][colSize];
        meltingIce = new ArrayDeque<>();
        time = 0;
    }
}