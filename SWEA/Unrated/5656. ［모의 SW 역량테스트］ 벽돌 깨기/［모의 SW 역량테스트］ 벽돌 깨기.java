/*
= SWEA 5656. [모의 SW 역량테스트] 벽돌 깨기

= 로직
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 구슬 개수, 맵의 가로 세로 입력
	1-2. 맵의 정보 입력
	1-3. 정답 초기화
2. 최적의 해 찾기
3. 구슬 발사
4. 벽돌 깨기
5. 벽돌 내리기
6. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int EMPTY = 0;
    static int marbleSize, rowSize, colSize;
    static int[][] originMap;
    static int minBrickCount;

    //2. 최적의 해 찾기
    public static void findBestWay(int marbleCnt, int[][] map) {
        if (marbleCnt == marbleSize) {
            //정답 업데이트
            minBrickCount = Math.min(countBrick(map), minBrickCount);

            return;
        }

        for (int col = 0; col < colSize; col++) {
            //3. 구슬 발사
            findBestWay(marbleCnt + 1, fallMarble(map, col));
        }
    }

    //3. 구슬 발사
    public static int[][] fallMarble(int[][] map, int col) {
        int[][] newMap = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; row++)
            newMap[row] = Arrays.copyOf(map[row], colSize);

        //구슬 떨어뜨리기
        int row = 0;
        while (row < rowSize && newMap[row][col] == EMPTY)
            row++;

        if (row < rowSize) {
            //4. 벽돌 깨기
            brickOut(newMap, row, col);

            //5. 벽돌 내리기
            fallBrick(newMap);
        }

        return newMap;
    }

    //4. 벽돌 깨기
    public static void brickOut(int[][] map, int row, int col) {
        int power = map[row][col];

        map[row][col] = EMPTY;

        for (int dist = 1; dist < power; dist++) {
            for (int[] delta : DELTA) {
                int nr = row + delta[0] * dist;
                int nc = col + delta[1] * dist;

                if (isOut(nr, nc))
                    continue;

                if (map[nr][nc] == EMPTY)
                    continue;

                brickOut(map, nr, nc);
            }
        }
    }

    //5. 벽돌 내리기
    public static void fallBrick(int[][] map) {
        for (int col = 0; col < colSize; col++) {
            int bottomRow = rowSize - 1; //다음으로 붙일 블록의 위치

            for (int row = rowSize - 1; row >= 0; row--) {
                if (map[row][col] == EMPTY)
                    continue;

                if (bottomRow != row) {
                    map[bottomRow][col] = map[row][col];
                    map[row][col] = EMPTY;
                }

                bottomRow--;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        int testCase = Integer.parseInt(input.readLine().trim());

        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            init();

            //2. 최적의 해 찾기
            findBestWay(0, originMap);

            //6. 출력
            output.append("#").append(tc).append(" ").append(minBrickCount).append("\n");
        }
        System.out.println(output);
    }

    public static int countBrick(int[][] map) {
        int brickCount = 0;

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (map[row][col] == EMPTY)
                    continue;

                brickCount++;
            }
        }

        return brickCount;
    }

    public static boolean isOut(int row, int col) {
        return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 구슬 개수, 맵의 가로 세로 입력
        st = new StringTokenizer(input.readLine().trim());
        marbleSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        rowSize = Integer.parseInt(st.nextToken());

        //1-2. 맵의 정보 입력
        originMap = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < colSize; col++) {
                originMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        //1-3. 정답 초기화
        minBrickCount = Integer.MAX_VALUE;
    }
}