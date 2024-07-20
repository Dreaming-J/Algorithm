import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/*
백준 14890번 경사로
NxN인 지도의 각 칸에 높이가 주어진다.
이 지도에서 지나갈 수 있는 길의 개수를 알아보자 한다.
    길 = 한 행 or 한 열 전부이며, 끝에서 끝까지 지나가는 것
    -> 길은 2N개 존재함
길을 지나가기 위해서는 모든 칸의 높이가 같거나, 경사로를 놓아서 지나갈 수 있어야 한다.
    경사로의 높이는 1이며, 길이는 L이다.
        - 경사로는 낮은 칸에 놓음
        - 경사로를 놓을 칸의 높이는 같아야 하고, L개의 칸이 연속
        - 경사로를 놓은 곳에 또 경사로를 놓을 수 없음
        - 경사로가 지도의 범위를 벗어나는 경우 놓을 수 없음

# 입력
    첫째 줄: N L
        N = 지도의 크기 (2 <= N <= 100)
        L = 경사로의 길이 (1 <= L <= N)
    둘째 줄부터 N개의 줄: 지도 (높이는 10 이하의 자연수)
# 출력
    지나갈 수 있는 길의 개수

# 로직
    0. 모든 길(가로, 세로)을 검사할 때까지 1부터 반복
        0-1. 가로줄 검사
        0-2. 세로줄 검사
    1. 길을 하나 선택 후, 탐색
    2. 길의 칸의 높이 탐색
    3. 이전 칸과 높이 비교
        3-1. 칸의 높이 차이가 1 초과인 경우, 탐색 종료
        3-2. 칸의 높이 차이가 1인 경우,
            이전 칸이 낮은 경우, L칸 "전"까지
            이전 칸이 높은 경우, L칸 "후"까지
            3-2-1. L칸 전/후 지도 범위 벗어나지 않는지 확인
            3-2-2. L칸 전/후 높이 동일한지 확인
            3-2-3. L칸 전/후 설치된 경사로 없는지 확인
            3-2-4. 위의 3개 조건이 모두 true라면 경사로 설치 후 탐색 진행
    4. 마지막까지 도달했다면 정답 개수 증가
 */

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int mapSize;
    static int rampLength;
    static int[][] map;
    static int answer = 0;

    public static boolean isInMap(int position) {
        //3-2-1. L칸 전/후 지도 범위 벗어나지 않는지 확인
        return position >= 0 && position < mapSize;
    }

    public static void main(String args[]) throws IOException {
        //입력
        st = new StringTokenizer(br.readLine());
        mapSize = Integer.parseInt(st.nextToken());
        rampLength = Integer.parseInt(st.nextToken());
        map = new int[mapSize][mapSize];
        for (int row = 0; row < mapSize; row++) {
            map[row] = Stream.of(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        //로직
        //0-1. 모든 가로줄을 검사할 때까지 1부터 반복
        //1. 길을 하나 선택 후, 탐색
        for (int row = 0; row < mapSize; row++) {
            boolean[] rampMap = new boolean[mapSize];
            boolean isCross = true;
            //2. 길의 칸의 높이 탐색
            for (int col = 1; col < mapSize; col++) {
                //3. 이전 칸과 높이 비교
                int gap = map[row][col - 1] - map[row][col];
                //3-1. 칸의 높이 차이가 1 초과인 경우, 탐색 종료
                if (Math.abs(gap) > 1) {
                    isCross = false;
                    break;
                }

                //3-2. 칸의 높이 차이가 1인 경우,
                if (Math.abs(gap) == 1) {
                    //이전 칸이 낮은 경우, L칸 "전"까지
                    //이전 칸이 높은 경우, L칸 "후"까지
                    int startIdx = col - (gap < 0 ? 1 : 0);
                    int height = gap < 0 ? map[row][col - 1] : map[row][col];

                    //3-2-1. L칸 전/후 지도 범위 벗어나지 않는지 확인
                    if (!isInMap(startIdx + gap * (rampLength - 1))) {
                        isCross = false;
                        break;
                    }

                    //3-2-2. L칸 전/후 높이 동일한지 확인
                    //3-2-3. L칸 전/후 설치된 경사로 없는지 확인
                    boolean canInstall = true;
                    for (int rampIdx = 0; rampIdx < rampLength; rampIdx++) {
                        if (map[row][startIdx + gap * rampIdx] != height || rampMap[startIdx + gap * rampIdx]) {
                            canInstall = false;
                            break;
                        }
                    }
                    if (!canInstall) {
                        isCross = false;
                        break;
                    }

                    //3-2-4. 위의 3개 조건이 모두 true라면 경사로 설치 후 탐색 진행
                    for (int rampIdx = 0; rampIdx < rampLength; rampIdx++) {
                        rampMap[startIdx + gap * rampIdx] = true;
                    }
                }
            }

            //4. 마지막까지 도달했다면 정답 개수 증가
            if (isCross)
                answer++;
        }

        //0-2. 모든 세로줄을 검사할 때까지 1부터 반복
        for (int col = 0; col < mapSize; col++) {
            boolean[] rampMap = new boolean[mapSize];
            boolean isCross = true;
            //2. 길의 칸의 높이 탐색
            for (int row = 1; row < mapSize; row++) {
                //3. 이전 칸과 높이 비교
                int gap = map[row - 1][col] - map[row][col];
                //3-1. 칸의 높이 차이가 1 초과인 경우, 탐색 종료
                if (Math.abs(gap) > 1) {
                    isCross = false;
                    break;
                }

                //3-2. 칸의 높이 차이가 1인 경우,
                if (Math.abs(gap) == 1) {
                    //이전 칸이 낮은 경우, L칸 "전"까지
                    //이전 칸이 높은 경우, L칸 "후"까지
                    int startIdx = row - (gap < 0 ? 1 : 0);
                    int height = gap < 0 ? map[row - 1][col] : map[row][col];

                    //3-2-1. L칸 전/후 지도 범위 벗어나지 않는지 확인
                    if (!isInMap(startIdx + gap * (rampLength - 1))) {
                        isCross = false;
                        break;
                    }

                    //3-2-2. L칸 전/후 높이 동일한지 확인
                    //3-2-3. L칸 전/후 설치된 경사로 없는지 확인
                    boolean canInstall = true;
                    for (int rampIdx = 0; rampIdx < rampLength; rampIdx++) {
                        if (map[startIdx + gap * rampIdx][col] != height || rampMap[startIdx + gap * rampIdx]) {
                            canInstall = false;
                            break;
                        }
                    }
                    if (!canInstall) {
                        isCross = false;
                        break;
                    }

                    //3-2-4. 위의 3개 조건이 모두 true라면 경사로 설치 후 탐색 진행
                    for (int rampIdx = 0; rampIdx < rampLength; rampIdx++) {
                        rampMap[startIdx + gap * rampIdx] = true;
                    }
                }
            }

            //4. 마지막까지 도달했다면 정답 개수 증가
            if (isCross)
                answer++;
        }

        System.out.println(answer);
    }
}