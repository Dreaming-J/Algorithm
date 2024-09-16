/*
= BOJ 7576. 토마토

= 로직
1. 초기 세팅
	1-1. 가로, 세로 입력
	1-2. 창고 정보 입력
        1-2-1. 익은 토마토 좌표 보관
        1-2-2. 익지 않은 토마토 개수 계산
2. 모든 토마토가 익는 일자 시뮬레이션
3. 출력
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
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int TOMATO = 0, RIPE_TOMATO = 1;
    static int rowSize, colSize;
    static int[][] storage;
    static Deque<Integer> ripeTomatos;
    static int day, tomatoCount;

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 모든 토마토가 익는 일자 시뮬레이션
        while (!ripeTomatos.isEmpty() && tomatoCount != 0) {
            int size = ripeTomatos.size();
            
            while (size-- > 0) {
                int cur = ripeTomatos.poll();
                int row = cur / colSize;
                int col = cur % colSize;

                for (int[] delta : DELTA) {
                    int nr = row + delta[0];
                    int nc = col + delta[1];

                    //범위를 벗어나면 패스
                    if (!canGo(nr, nc))
                        continue;

                    //인접한 칸이 익지 않은 토마토가 아니라면 패스
                    if (storage[nr][nc] != TOMATO)
                        continue;
                    
                    tomatoCount--;
                    storage[nr][nc] = RIPE_TOMATO;
                    ripeTomatos.add(nr * colSize + nc);
                }
            }

            day++;
        }

        //3. 출력
        System.out.print(tomatoCount == 0 ? day : -1);
    }

    public static boolean canGo(int row, int col) {
        return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 가로, 세로 입력
        st = new StringTokenizer(input.readLine());
        colSize = Integer.parseInt(st.nextToken());
        rowSize = Integer.parseInt(st.nextToken());

        //1-2. 창고 정보 입력
        storage = new int[rowSize][colSize];
        ripeTomatos = new ArrayDeque<>();
        for (int row = 0; row < rowSize; row++) {
            st = new StringTokenizer(input.readLine());
            for (int col = 0; col < colSize; col++) {
                storage[row][col] = Integer.parseInt(st.nextToken());

                //1-2-1. 익은 토마토 좌표 보관
                if (storage[row][col] == RIPE_TOMATO) {
                    ripeTomatos.add(row * colSize + col);
                }

                //1-2-2. 익지 않은 토마토 개수 계산
                if (storage[row][col] == TOMATO) {
                    tomatoCount++;
                }
            }
        }
    }
}