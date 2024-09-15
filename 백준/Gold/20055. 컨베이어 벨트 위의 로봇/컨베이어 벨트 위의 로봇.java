/*
= BOJ 20055. 컨베이어 벨트 위의 로봇

= 로직
1. 초기 세팅
    1-1. 컨베이어 벨트 길이, 내구도 0인 칸의 개수 입력
    1-2. 컨베이어 벨트 내구도 입력
    1-3. 변수 초기화
2. 로봇 옮기기 시뮬레이션
    2-1. 단계 증가
    2-2. 벨트 회전
    2-3. 내리는 위치에 로봇 내리기
    2-4. 먼저 올라간 로봇부터 모든 로봇 이동
    2-5. 내리는 위치에 로봇 내리기
    2-6. 올리는 위치에 로봇 올리기
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int BROKEN = 0;
    static int conveyorBeltSize, zeroDurabilitySize;
    static int[] durabilities;
    static int stage, zeroDurabilityCount;
    static boolean[] robots;

    //2. 로봇 옮기기 시뮬레이션
    public static void simulation() {
        int raisePosition = 0;

        while (zeroDurabilityCount < zeroDurabilitySize) {
            //2-1. 단계 증가
            stage++;

            //2-2. 벨트 회전
            if (--raisePosition < 0)
                raisePosition = conveyorBeltSize - 1;

            //2-3. 내리는 위치에 로봇 내리기
            int dropPosition = raisePosition + (conveyorBeltSize / 2) - 1;
            robots[dropPosition % conveyorBeltSize] = false;

            //2-4. 먼저 올라간 로봇부터 모든 로봇 이동
            for (int idx = conveyorBeltSize / 2 - 2; idx >= 0; idx--) {
                int curIdx = (raisePosition + idx) % conveyorBeltSize;
                int nextIdx = (raisePosition + idx + 1) % conveyorBeltSize;

                if (robots[curIdx] && !robots[nextIdx] && durabilities[nextIdx] != BROKEN) {
                    robots[curIdx] = false;
                    robots[nextIdx] = true;
                    zeroDurabilityCount += --durabilities[nextIdx] == BROKEN ? 1 : 0;
                }
            }

            //2-5. 내리는 위치에 로봇 내리기
            robots[dropPosition % conveyorBeltSize] = false;

            //2-6. 올리는 위치에 로봇 올리기
            if (durabilities[raisePosition % conveyorBeltSize] != BROKEN) {
                robots[raisePosition % conveyorBeltSize] = true;
                zeroDurabilityCount += --durabilities[raisePosition % conveyorBeltSize] == BROKEN ? 1 : 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 로봇 옮기기 시뮬레이션
        simulation();

        //3. 출력
        System.out.print(stage);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 컨베이어 벨트 길이, 내구도 0인 칸의 개수 입력
        st = new StringTokenizer(input.readLine());
        conveyorBeltSize = Integer.parseInt(st.nextToken()) * 2;
        zeroDurabilitySize = Integer.parseInt(st.nextToken());

        //1-2. 컨베이어 벨트 내구도 입력
        durabilities = new int[conveyorBeltSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < conveyorBeltSize; idx++) {
            durabilities[idx] = Integer.parseInt(st.nextToken());
        }

        //1-3. 변수 초기화
        robots = new boolean[conveyorBeltSize];
    }
}
