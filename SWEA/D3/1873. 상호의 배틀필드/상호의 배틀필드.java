/*
SWEA 1873. 상호의 배틀필드

0. 테스트 케이스 입력
1. 초기 세팅
    1-1. 맵의 행과 열 입력
    1-2. 행의 크기만큼 맵의 정보 입력
        1-2-1. 탱크 입력이라면 보관
    1-3. 명령어의 개수 입력
    1-4. 명령어 입력
2. 게임 시뮬레이션 실행
    2-1. 포탄 발사 명령일 경우
        2-1-1. 맵 범위를 벗어날 때까지 이동
        2-1-2. 벽돌 벽을 만났을 경우, 벽을 평지로 변경 후 종료
        2-1-3. 강철 벽을 만났을 경우, 종료
    2-2. 이동 명령일 경우
        2-2-1. 이동할 위치 계산
        2-2-2. 탱크의 바라보는 방향 변경
        2-2-3. 맵 범위를 벗어났을 경우 패스
        2-2-4. 이동할 위치가 평지가 아닌 경우 패스
        2-2-5. 이동할 정보 지도에 반영
        2-2-6. 탱크의 위치 이동
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final char LAND = '.', BRICK_WALL = '*', STEEL_WALL = '#', WATER = '-';
    static final char UP_TANK = '^', DOWN_TANK = 'v', LEFT_TANK = '<', RIGHT_TANK = '>';
    static final char CMD_SHOOT = 'S';
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int testCase;
    static int rowSize, colSize, commandSize;
    static char[][] map;
    static char[] commands;
    static Tank tank;

    //2-1. 포탄 발사 명령일 경우
    public static void shoot() {
        Command command = Command.of(tank.direction);

        int shellRow = tank.row + deltas[command.ordinal()][0];
        int shellCol = tank.col + deltas[command.ordinal()][1];

        //2-1-1. 맵 범위를 벗어날 때까지 이동
        while (canGo(shellRow, shellCol)) {
            //2-1-2. 벽돌 벽을 만났을 경우, 벽을 평지로 변경 후 종료
            if (map[shellRow][shellCol] == BRICK_WALL) {
                map[shellRow][shellCol] = LAND;
                return;
            }

            //2-1-3. 강철 벽을 만났을 경우, 종료
            if (map[shellRow][shellCol] == STEEL_WALL)
                return;

            shellRow += deltas[command.ordinal()][0];
            shellCol += deltas[command.ordinal()][1];
        }
    }

    //2-2. 이동 명령일 경우
    public static void moveTank(char input) {
        //2-2-1. 이동할 위치 계산
        Command command = Command.of(input);
        int nr = tank.row + deltas[command.ordinal()][0];
        int nc = tank.col + deltas[command.ordinal()][1];

        //2-2-2. 탱크의 바라보는 방향 변경
        tank.direction = command.direction;
        map[tank.row][tank.col] = command.direction;

        //2-2-3. 맵 범위를 벗어났을 경우 패스
        if (!canGo(nr, nc))
            return;

        //2-2-4. 이동할 위치가 평지가 아닌 경우 패스
        if (map[nr][nc] != LAND)
            return;

        //2-2-5. 이동할 정보 지도에 반영
        map[tank.row][tank.col] = LAND;
        map[nr][nc] = command.direction;

        //2-2-6. 탱크의 위치 이동
        tank.row = nr;
        tank.col = nc;
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            //1. 초기 세팅
            initTestCase();

            //2. 게임 시뮬레이션 실행
            for (char input : commands) {
                //2-1. 포탄 발사 명령일 경우
                if (input == CMD_SHOOT) {
                    shoot();
                    continue;
                }

                //2-2. 이동 명령일 경우
                moveTank(input);
            }

            //4. 출력
            output.append("#").append(tc).append(" ");
            for (char[] row : map) {
                for (char col : row) {
                    output.append(col);
                }
                output.append("\n");
            }
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
        //1-1. 맵의 행과 열 입력
        st = new StringTokenizer(input.readLine().trim());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());

        //1-2. 행의 크기만큼 맵의 정보 입력
        map = new char[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            String line = input.readLine().trim();
            for (int col = 0; col < colSize; col++) {
                map[row][col] = line.charAt(col);

                //1-2-1. 탱크 입력이라면 보관
                if (map[row][col] != LAND && map[row][col] != BRICK_WALL && map[row][col] != STEEL_WALL
                        && map[row][col] != WATER)
                    tank = new Tank(row, col, map[row][col]);
            }
        }

        //1-3. 명령어의 개수 입력
        commandSize = Integer.parseInt(input.readLine().trim());

        //1-4. 명령어 입력
        commands = input.readLine().trim().toCharArray();
    }

    public enum Command {
        UP('U', UP_TANK),
        DOWN('D', DOWN_TANK),
        LEFT('L', LEFT_TANK),
        RIGHT('R', RIGHT_TANK);

        final char input;
        final char direction;

        Command(char input, char direction) {
            this.input = input;
            this.direction = direction;
        }

        public static Command of(char input) {
            for (Command command : values()) {
                if (command.input == input || command.direction == input)
                    return command;
            }

            return null;
        }
    }

    public static class Tank {
        int row;
        int col;
        char direction;

        public Tank(int row, int col, char direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }
    }
}