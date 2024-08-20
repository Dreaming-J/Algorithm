/*
=BOJ 16926. 배열 돌리기 1

1. 배열의 세로, 가로, 그리고 회전 수 입력
2. 세로 길이만큼 배열 정보 입력
3. 회전
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int DIRECTION_SIZE = 4, NEXT_CIRCLE = 4;
    static final int[][] deltas = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}}; //하, 우, 상, 좌, 우하 델타 배열
    static int rowSize, colSize, rotateCount;
    static int[][] map;

    public static class Position {
        int row;
        int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void rotateMap() {
        for (int count = 0; count < Math.min(rowSize, colSize) / 2; count++) {
            rotateCircle(count);
        }
    }

    public static void rotateCircle(int count) {
        int prevValue = map[count][count];
        int temp;

        Position pos = new Position(count, count);
        int direction = 0;

        do {
            //다음 칸으로 이동
            if (!canGo(pos.row + deltas[direction][0], pos.col + deltas[direction][1], count))
                direction = (direction + 1) % DIRECTION_SIZE;
            pos.row = pos.row + deltas[direction][0];
            pos.col = pos.col + deltas[direction][1];

            //이전 칸 값 가져오기
            temp = map[pos.row][pos.col];
            map[pos.row][pos.col] = prevValue;
            prevValue = temp;

        } while (!(pos.row == count && pos.col == count));
    }

    public static boolean canGo(int row, int col, int bias) {
        return row >= bias && row < rowSize - bias && col >= bias && col < colSize - bias;
    }

    public static void main(String[] args) throws IOException {
        //1. 배열의 세로, 가로, 그리고 회전 수 입력
        st = new StringTokenizer(input.readLine().trim());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        rotateCount = Integer.parseInt(st.nextToken());

        //2. 세로 길이만큼 배열 정보 입력
        map = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            st = new StringTokenizer(input.readLine().trim());
            for (int col = 0; col < colSize; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        //3. 회전
        for (int count = 0; count < rotateCount; count++) {
            rotateMap();
        }

        //4. 출력
        for (int[] line : map) {
            for (int element : line) {
                output.append(element).append(" ");
            }
            output.append("\n");
        }
        System.out.println(output);
    }
}
