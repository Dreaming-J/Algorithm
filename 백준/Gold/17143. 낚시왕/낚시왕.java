/*
= BOJ 17143. 낚시왕

= 특이사항
격자판의 정보는 100_000 * 시간 + 상어 번호로 저장

1=위, 2=아래, 3=오른쪽, 4=왼쪽
한 칸에 상어가 두 마리 이상 있다면 크기가 가장 큰 상어가 모두 잡아먹는다.
상어가 이동 시, 격자판을 넘어간다면 방향을 바꿔서 마저 이동한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int TIME = 100_000;
    static final int[][] DELTA = {{0, 0}, {-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    static final int UP = 1, DOWN = 2;
    static int rowSize, colSize, sharkSize;
    static int[][] board;
    static Shark[] sharks;
    static int catchSharkSize, catchSharkCount;

    //상어 잡기
    public static void catchShark(int time) {
        for (int row = 1; row <= rowSize; row++) {
            int sharkTime = board[row][time] / TIME;
            int sharkNum = board[row][time] % TIME;

            //해당 위치에 상어 존재하지 않으면 패스
            if (time != sharkTime)
                continue;

            //상어가 존재한다면 잡은 후, 상어 잡기 종료
            sharks[sharkNum].isCatch = true;
            catchSharkSize += sharks[sharkNum].size;
            catchSharkCount++;
            break;
        }
    }

    //상어 이동
    public static void moveShark(int time) {
        for (int sharkNum = 1; sharkNum <= sharkSize; sharkNum++) {
            Shark shark = sharks[sharkNum];

            //이미 잡힌 상어라면 패스
            if (shark.isCatch)
                continue;

            //상어 이동
            shark.move();

            //격자판에 상어가 없다면 상어 표시
            if (board[shark.row][shark.col] / TIME < time) {
                board[shark.row][shark.col] = time * TIME + sharkNum;
            }

            //격자판에 상어가 있다면 더 큰 상어가 살아남음
            else if (board[shark.row][shark.col] / TIME == time) {
                int boardSharkNum = board[shark.row][shark.col] % TIME;

                //격자판의 상어가 더 작다면 잡아먹히고 격자판 갱신
                if (sharks[boardSharkNum].size < shark.size) {
                    sharks[boardSharkNum].isCatch = true;
                    board[shark.row][shark.col] = time * TIME + sharkNum;
                }

                //현재 상어가 더 작다면 잡아먹힘
                else
                    shark.isCatch = true;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //초기 세팅
        init();

        //시뮬레이션
        for (int time = 1; time <= colSize; time++) {
            //상어 잡기
            catchShark(time);

            //모든 상어를 잡았다면 시뮬레이션 종료
            if (catchSharkCount == sharkSize)
                break;

            //상어 이동
            moveShark(time + 1);
        }

        //정답 출력
        System.out.println(catchSharkSize);
    }

    public static class Shark {
        int row, col;
        int speed, direction, size;
        boolean isCatch;

        public Shark(int row, int col, int speed, int direction, int size) {
            this.row = row;
            this.col = col;
            this.speed = speed;
            this.direction = direction;
            this.size = size;
            this.isCatch = false;
        }

        //상어가 이동 시, 격자판을 넘어간다면 방향을 바꿔서 마저 이동한다.
        public void move() {
            int count = speed % (direction == UP || direction == DOWN ? 2 * (rowSize - 1) : 2 * (colSize - 1));

            for (int cnt = 0; cnt < count; cnt++) {
                if (!canGo(row + DELTA[direction][0], col + DELTA[direction][1])) {
                    direction += direction % 2 == 0 ? -1 : 1;
                }

                row += DELTA[direction][0];
                col += DELTA[direction][1];
            }
        }
    }

    public static boolean canGo(int row, int col) {
        return row > 0 && row <= rowSize && col > 0 && col <= colSize;
    }

    //초기 세팅
    public static void init() throws IOException {
        //격자판의 크기와 상어의 수 입력
        st = new StringTokenizer(input.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        sharkSize = Integer.parseInt(st.nextToken());

        //격자판 생성
        board = new int[rowSize + 1][colSize + 1];

        //상어 정보 입력
        sharks = new Shark[sharkSize + 1];
        for (int num = 1; num <= sharkSize; num++) {
            st = new StringTokenizer(input.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());

            sharks[num] = new Shark(row, col, speed, direction, size);
            board[row][col] = TIME + num;
        }
    }
}
