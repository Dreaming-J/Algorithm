/*
=SWEA 2105. [모의 SW 역량테스트] 디저트 카페

=특이사항
내려가는 경우의 수만 정하면, 올라오는 경로가 정해짐

=로직
0. 테스트케이스 입력
1. 초기 세팅
	1-1. 지역의 크기 입력
	1-2. 지역의 크기만큼 디저트 카페 정보 입력
	1-3. 변수 초기화
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int MAX_DESSERT_SIZE = 100;
    static int testCase;
    static int mapSize;
    static int[][] map;
    static boolean[] eatDessert;
    static int startRow, startCol;
    static int maxEatDessert;

    public enum Direction {
        DOWN_RIGHT(new int[]{1, 1}),
        DOWN_LEFT(new int[]{1, -1}),
        UP_LEFT(new int[]{-1, -1}),
        UP_RIGHT(new int[]{-1, 1});

        final int[] delta;

        private Direction(int[] delta) {
            this.delta = delta;
        }

        public Direction next() {
            return Direction.values()[(ordinal() + 1) % 4];
        }
    }

    public static void findRoute(int row, int col, Direction direction, int dessertCount, int downRightCount,
                                 int downLeftCount) {
        //2-1. 원점에 돌아왔다면 종료
        if (row == startRow && col == startCol && direction == Direction.UP_RIGHT && downRightCount == 0
                && downLeftCount == 0) {
            maxEatDessert = Math.max(dessertCount, maxEatDessert);
            return;
        }

        //2-2. 현재 위치가 범위를 벗어났다면 종료
        if (!canGo(row, col))
            return;

        //2-3. 현재 위치가 이미 먹은 디저트라면 종료
        if (eatDessert[map[row][col]])
            return;
        eatDessert[map[row][col]] = true;

        //2-4. 다음으로 이동
        if (direction == Direction.DOWN_RIGHT) {
            findRoute(row + direction.delta[0], col + direction.delta[1], direction, dessertCount + 1,
                    downRightCount + 1, downLeftCount);

            Direction next = direction.next();
            findRoute(row + next.delta[0], col + next.delta[1], next, dessertCount + 1, downRightCount,
                    downLeftCount + 1);
        }

        if (direction == Direction.DOWN_LEFT) {
            findRoute(row + direction.delta[0], col + direction.delta[1], direction, dessertCount + 1, downRightCount,
                    downLeftCount + 1);

            Direction next = direction.next();
            findRoute(row + next.delta[0], col + next.delta[1], next, dessertCount + 1, downRightCount - 1,
                    downLeftCount);
        }

        if (direction == Direction.UP_LEFT) {
            if (downRightCount > 0)
                findRoute(row + direction.delta[0], col + direction.delta[1], direction, dessertCount + 1,
                        downRightCount - 1, downLeftCount);
            else {
                Direction next = direction.next();
                findRoute(row + next.delta[0], col + next.delta[1], next, dessertCount + 1, downRightCount,
                        downLeftCount - 1);
            }
        }

        if (direction == Direction.UP_RIGHT) {
            if (downLeftCount > 0)
                findRoute(row + direction.delta[0], col + direction.delta[1], direction, dessertCount + 1,
                        downRightCount, downLeftCount - 1);
        }

        eatDessert[map[row][col]] = false;
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < mapSize && col >= 0 && col < mapSize;
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            initTestCase();

            //2. 디저트 카페 방문
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    eatDessert = new boolean[MAX_DESSERT_SIZE + 1];

                    startRow = row;
                    startCol = col;
                    findRoute(row, col, Direction.DOWN_RIGHT, 0, 0, 0);
                }
            }

            //3. 출력
            output.append("#").append(tc).append(" ").append(maxEatDessert).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
        //1-1. 지역의 크기 입력
        mapSize = Integer.parseInt(input.readLine().trim());

        //1-2. 지역의 크기만큼 디저트 카페 정보 입력
        map = new int[mapSize][mapSize];
        for (int row = 0; row < mapSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < mapSize; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        //1-3. 변수 초기화
        maxEatDessert = -1;
    }
}
