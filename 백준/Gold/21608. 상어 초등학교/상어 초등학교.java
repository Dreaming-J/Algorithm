/*
= BOJ 21608. 상어 초등학교

= 로직
1. 초기 세팅
    1-1. 교실의 크기 입력
    1-2. 학생 번호와 해당 학생이 좋아하는 학생 4명의 번호 입력
    1-3. 변수 초기화
2. 좌석 배정 시작
3. 해당 위치 좌석의 정보 계산
4. 배정된 좌석의 만족도 계산
5. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int FAVORITE_SIZE = 4, EMPTY = 0;
    static int roomSize, studentSize;
    static int[][] room;
    static int[] orders;
    static int[][] favorites;
    static int totalScore;

    //2. 좌석 배정 시작
    public static void setSeat(int student) {
        Seat bestSeat = null;

        for (int row = 0; row < roomSize; row++) {
            for (int col = 0; col < roomSize; col++) {
                //이미 배정된 좌석이라면 패스
                if (room[row][col] != EMPTY)
                    continue;

                //3. 해당 위치 좌석의 정보 계산
                //더 최적의 좌석 선택
                bestSeat = Seat.max(makeSeat(student, row, col), bestSeat);
            }
        }

        //최적의 좌석에 해당 학생 배치
        room[bestSeat.row][bestSeat.col] = student;
    }

    //3. 해당 위치 좌석의 정보 계산
    public static Seat makeSeat(int student, int row, int col) {
        Seat seat = new Seat(row, col);

        //주변 칸 확인
        for (int[] delta : DELTA) {
            int nr = row + delta[0];
            int nc = col + delta[1];

            //범위를 벗어났다면 패스
            if (!canGo(nr, nc))
                continue;

            //비어있는 칸 개수
            if (room[nr][nc] == EMPTY) {
                seat.emptyCount++;
                continue;
            }

            //좋아하는 학생 개수
            for (int favorite : favorites[student]) {
                if (room[nr][nc] == favorite) {
                    seat.favoriteCount++;
                    break;
                }
            }
        }

        return seat;
    }

    //4. 배정된 좌석의 만족도 계산
    public static int calScore(int student, int row, int col) {
        int favoriteCount = 0;

        for (int[] delta : DELTA) {
            int nr = row + delta[0];
            int nc = col + delta[1];

            //범위를 벗어났다면 패스
            if (!canGo(nr, nc))
                continue;

            //좋아하는 학생 개수
            for (int favorite : favorites[student]) {
                if (room[nr][nc] == favorite) {
                    favoriteCount++;
                    break;
                }
            }
        }

        return (int) Math.pow(10, favoriteCount - 1);
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 좌석 배정 시작
        for (int student : orders) {
            setSeat(student);
        }

        //4. 배정된 좌석의 만족도 계산
        for (int row = 0; row < roomSize; row++) {
            for (int col = 0; col < roomSize; col++) {
                totalScore += calScore(room[row][col], row, col);
            }
        }

        //5. 출력
        System.out.print(totalScore);
    }

    public static class Seat implements Comparable<Seat> {
        int row;
        int col;
        int favoriteCount;
        int emptyCount;

        public Seat(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public static Seat max(Seat o1, Seat o2) {
            if (o2 == null)
                return o1;

            return o1.compareTo(o2) > 0 ? o1 : o2;
        }

        @Override
        public int compareTo(Seat o) {
            //인접한 좋아하는 학생의 수가 같다면
            if (favoriteCount == o.favoriteCount) {
                //비어있는 칸의 수가 같다면
                if (emptyCount == o.emptyCount) {
                    //행의 번호가 같다면
                    if (row == o.row) {
                        //열의 번호가 더 작은가를 기준으로 비교
                        return Integer.compare(o.col, col);
                    }

                    //행의 번호가 더 작은가를 기준으로 비교
                    return Integer.compare(o.row, row);
                }

                //비어있는 칸의 수가 더 큰가를 기준으로 비교
                return Integer.compare(emptyCount, o.emptyCount);
            }

            //인접한 좋아하는 학생의 수가 더 많은가를 기준으로 비교
            return Integer.compare(favoriteCount, o.favoriteCount);
        }
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < roomSize && col >= 0 && col < roomSize;
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 교실의 크기 입력
        roomSize = Integer.parseInt(input.readLine());
        studentSize = roomSize * roomSize;

        //1-2. 학생 번호와 해당 학생이 좋아하는 학생 4명의 번호 입력
        orders = new int[studentSize];
        favorites = new int[studentSize + 1][FAVORITE_SIZE];
        for (int idx = 0; idx < studentSize; idx++) {
            st = new StringTokenizer(input.readLine());

            int num = Integer.parseInt(st.nextToken());
            orders[idx] = num;

            for (int favoriteIdx = 0; favoriteIdx < FAVORITE_SIZE; favoriteIdx++) {
                favorites[num][favoriteIdx] = Integer.parseInt(st.nextToken());
            }
        }

        //1-3. 변수 초기화
        room = new int[roomSize][roomSize];
    }
}
