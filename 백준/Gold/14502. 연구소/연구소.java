/*
= BOJ 14502. 연구소

= 로직
1. 초기 세팅
    1-1. 지도의 세로, 가로 크기 입력
    1-2. 지도 정보 입력(0 = 빈 칸, 1 = 벽, 2 = 바이러스)
    1-3. 변수 초기화
2. 3개의 벽 선택
3. 안전 영역 크기 계산
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int EMPTY = 0, WALL = 1, VIRUS = 2, WALL_SIZE = 3;
    static int rowSize, colSize;
    static int[][] map;
    static List<Point> viruses;
    static int totalSafetyArea, maxSafetyArea;

    //2. 3개의 벽 선택
    public static void chooseWall(int position, int wallCount) {
        if (wallCount == WALL_SIZE) {
            //3. 안전 영역 크기 계산
            maxSafetyArea = Math.max(findSafetyArea(), maxSafetyArea);

            return;
        }

        if (position == rowSize * colSize)
            return;

        int row = position / colSize;
        int col = position % colSize;
        if (map[row][col] == EMPTY) {
            map[row][col] = WALL;
            chooseWall(position + 1, wallCount + 1);
            map[row][col] = EMPTY;
        }
        chooseWall(position + 1, wallCount);
    }

    //3. 안전 영역 크기 계산
    public static int findSafetyArea() {
        int safetyArea = totalSafetyArea;

        boolean[][] visited = new boolean[rowSize][colSize];
        Deque<Point> queue = new ArrayDeque<>();

        for (Point virus : viruses) {
            visited[virus.row][virus.col] = true;
            queue.add(virus);
        }

        while (!queue.isEmpty()) {
            Point cur = queue.poll();

            safetyArea--;

            for (int[] delta : DELTA) {
                int nr = cur.row + delta[0];
                int nc = cur.col + delta[1];

                if (!canGo(nr, nc))
                    continue;

                if (visited[nr][nc])
                    continue;

                if (map[nr][nc] != EMPTY)
                    continue;

                visited[nr][nc] = true;
                queue.add(new Point(nr, nc));
            }
        }

        return safetyArea;
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 3개의 벽 선택
        chooseWall(0, 0);

        //4.출력
        System.out.print(maxSafetyArea);
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 & col < colSize;
    }

    public static class Point {
        int row, col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 지도의 세로, 가로 크기 입력
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        //1-2. 지도 정보 입력(0 = 빈 칸, 1 = 벽, 2 = 바이러스)
        map = new int[rowSize][colSize];
        totalSafetyArea = -WALL_SIZE;
        viruses = new ArrayList<>();

        for (int row = 0; row < rowSize; row++) {
            st = new StringTokenizer(input.readLine());
            for (int col = 0; col < colSize; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());

                if (map[row][col] != WALL)
                    totalSafetyArea++;

                if (map[row][col] == VIRUS)
                    viruses.add(new Point(row, col));
            }
        }

        //1-3. 변수 초기화
        maxSafetyArea = Integer.MIN_VALUE;
    }
}
