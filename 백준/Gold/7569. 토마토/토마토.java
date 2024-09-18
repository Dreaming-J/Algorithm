/*
= BOJ 7569. 토마토
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
    static final int[][] DELTA = {{0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}, {-1, 0, 0}, {1, 0, 0}};
    static final int TOMATO = 0, RIPE_TOMATO = 1;
    static int rowSize, colSize, heightSize;
    static int[][][] storage;
    static Deque<Point> ripeTomatos;
    static int day, tomatoCount;

    public static void main(String[] args) throws IOException {
        //초기 세팅
        init();

        //모든 토마토가 익는 일자 시뮬레이션
        while (!ripeTomatos.isEmpty() && tomatoCount != 0) {
            int size = ripeTomatos.size();

            while (size-- > 0) {
                Point cur = ripeTomatos.poll();

                for (int[] delta : DELTA) {
                    int nh = cur.height + delta[0];
                    int nr = cur.row + delta[1];
                    int nc = cur.col + delta[2];

                    //범위를 벗어나면 패스
                    if (!canGo(nh, nr, nc))
                        continue;

                    //인접한 칸이 익지 않은 토마토가 아니라면 패스
                    if (storage[nh][nr][nc] != TOMATO)
                        continue;

                    tomatoCount--;
                    storage[nh][nr][nc] = RIPE_TOMATO;
                    ripeTomatos.add(new Point(nh, nr, nc));
                }
            }

            day++;
        }

        //출력
        System.out.print(tomatoCount == 0 ? day : -1);
    }

    public static boolean canGo(int height, int row, int col) {
        return height >= 0 && height < heightSize && row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }

    public static class Point {
        int height, row, col;

        public Point(int height, int row, int col) {
            this.height = height;
            this.row = row;
            this.col = col;
        }
    }

    //초기 세팅
    public static void init() throws IOException {
        //가로, 세로, 높이 입력
        st = new StringTokenizer(input.readLine());
        colSize = Integer.parseInt(st.nextToken());
        rowSize = Integer.parseInt(st.nextToken());
        heightSize = Integer.parseInt(st.nextToken());

        //창고 정보 입력
        storage = new int[heightSize][rowSize][colSize];
        ripeTomatos = new ArrayDeque<>();
        for (int height = 0; height < heightSize; height++) {
            for (int row = 0; row < rowSize; row++) {
                st = new StringTokenizer(input.readLine());
                for (int col = 0; col < colSize; col++) {
                    storage[height][row][col] = Integer.parseInt(st.nextToken());

                    //익은 토마토 좌표 보관
                    if (storage[height][row][col] == RIPE_TOMATO) {
                        ripeTomatos.add(new Point(height, row, col));
                    }

                    //익지 않은 토마토 개수 계산
                    if (storage[height][row][col] == TOMATO) {
                        tomatoCount++;
                    }
                }
            }
        }
    }
}