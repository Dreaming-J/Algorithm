/*
= BOJ 2933. 미네랄

= 특이 사항
한 번에 1개의 클러스터만 떨어지는 경우 보장

= 로직
1. 초기 세팅
	1-1. 동굴의 크기 입력
	1-2. 동굴의 정보 입력
	1-3. 막대 던지는 횟수 입력
	1-4. 막대 던지는 높이 입력
2. 막대 던지기
3. 떨어지는 클러스터가 있는지 탐색
    3-1. 주변에 미네랄이 있는지 파악
    3-2. 클러스터 탐색
4. 떨어질 클러스터가 있다면 떨어뜨리기
5. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int UP = 1, DOWN = 0;
    static final char EMPTY = '.', MINERAL = 'x';
    static int rowSize, colSize, throwSize;
    static char[][] cave;
    static int[] throwHeight;
    static boolean[][] visited;
    static Deque<Point> queue;
    static PriorityQueue<Point> pq;

    //3-2. 떨어지는 클러스터인지 탐색
    public static boolean canFall(Point point) {
        visited[point.row][point.col] = true;
        queue.add(point);
        pq.add(point);

        boolean canFall = true;
        while (!queue.isEmpty()) {
            Point cur = queue.poll();

            //땅에 도착했으면 떨어질 클러스터가 없음
            if (cur.row == 0)
                canFall = false;

            //다음 칸으로 이동
            for (int[] delta : DELTA) {
                int nr = cur.row + delta[0];
                int nc = cur.col + delta[1];

                if (!canGo(nr, nc))
                    continue;

                if (visited[nr][nc])
                    continue;

                if (cave[nr][nc] == EMPTY)
                    continue;

                visited[nr][nc] = true;
                queue.add(new Point(nr, nc));
                pq.add(new Point(nr, nc));
            }
        }

        if (!canFall) {
            queue.clear();
            pq.clear();
        }

        return canFall;
    }

    //3. 떨어지는 클러스터가 있는지 탐색
    public static void findCluster(int row, int col) {
        visited = new boolean[rowSize][colSize];

        //3-1. 주변에 미네랄이 있는지 파악
        for (int[] delta : DELTA) {
            int nr = row + delta[0];
            int nc = col + delta[1];

            if (!canGo(nr, nc))
                continue;

            if (visited[nr][nc])
                continue;

            if (cave[nr][nc] == EMPTY)
                continue;

            //3-2. 떨어지는 클러스터인지 탐색
            if (canFall(new Point(nr, nc))) {
                break;
            }
        }

        //4. 떨어질 클러스터가 있다면 떨어뜨리기
        if (!pq.isEmpty())
            fallCluster();
    }

    //4. 떨어질 클러스터가 있다면 떨어뜨리기
    public static void fallCluster() {
        boolean attachedAnotherCluster = false;
        PriorityQueue<Point> temp = new PriorityQueue<>();

        while (!attachedAnotherCluster) {
            while (!pq.isEmpty()) {
                Point cur = pq.poll();

                cave[cur.row][cur.col] = EMPTY;

                int downRow = cur.row + DELTA[DOWN][0];
                int downCol = cur.col + DELTA[DOWN][1];

                temp.add(new Point(downRow, downCol));
            }

            while (!temp.isEmpty()) {
                Point cur = temp.poll();

                cave[cur.row][cur.col] = MINERAL;

                int downRow = cur.row + DELTA[DOWN][0];
                int downCol = cur.col + DELTA[DOWN][1];

                if (cur.row == 0 || cave[downRow][downCol] == MINERAL)
                    attachedAnotherCluster = true;

                pq.add(cur);
            }

            if (attachedAnotherCluster) {
                pq.clear();
                return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 막대 던지기
        for (int throwIdx = 0; throwIdx < throwSize; throwIdx++) {
            int row = throwHeight[throwIdx];

            //2-1. 왼쪽과 오른쪽을 번갈아 가면서 던지기
            for (int col = throwIdx % 2 == 0 ? 0 : colSize - 1;
                 col < colSize && col >= 0;
                 col += throwIdx % 2 == 0 ? 1 : -1) {
                if (cave[row][col] == MINERAL) {
                    cave[row][col] = EMPTY;

                    //3. 떨어지는 클러스터가 있는지 탐색
                    findCluster(row, col);

                    break;
                }
            }
        }

        //5. 출력
        for (int row = rowSize - 1; row >= 0; row--) {
            for (int col = 0; col < colSize; col++) {
                output.append(cave[row][col]);
            }
            output.append("\n");
        }

        System.out.println(output);
    }

    public static class Point implements Comparable<Point> {
        int row, col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Point o) {
            return row == o.row ? col - o.col : o.row - row;
        }
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 동굴의 크기 입력
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        //1-2. 동굴의 정보 입력
        cave = new char[rowSize][colSize];
        for (int row = rowSize - 1; row >= 0; row--) {
            String line = input.readLine();
            for (int col = 0; col < colSize; col++) {
                cave[row][col] = line.charAt(col);
            }
        }

        //1-3. 막대 던지는 횟수 입력
        throwSize = Integer.parseInt(input.readLine());

        //1-4. 막대 던지는 높이 입력
        throwHeight = new int[throwSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < throwSize; idx++) {
            throwHeight[idx] = Integer.parseInt(st.nextToken()) - 1;
        }

        //1-5. 변수 초기화
        queue = new ArrayDeque<>();
        pq = new PriorityQueue<>();
    }
}
