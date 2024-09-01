/*
= BOJ 14676. 영우는 사기꾼?

= 특이사항
위상 정렬
건물의 번호는 1번부터 시작

= 로직
1. 초기 세팅
    1-1. 건물의 개수, 건물 사이의 관계의 개수, 게임 정보의 개수 입력
    1-2. 건물의 관계의 개수만큼 관계 정보 입력
    1-3. 변수 초기화
2. 게임 정보의 개수만큼 정보 입력 후 처리
3. 건물 건설 처리
    3-1. 아직 지을 수 없는 건물이라면 치트키 사용으로 판단
    3-2. 해당 건물이 처음으로 지어졌다면 영향을 미치는 건물의 영향 제거
4. 건물 파괴 처리
    4-1. 현재 지어지지 않은 건물이라면 치트키 사용으로 판단
    4-2. 해당 건물이 더 이상 존재하지 않는다면 영향력 추가
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input;
    static StringTokenizer st;
    static final String BUILD = "1", DESTROY = "2";
    static int buildingSize, preBuildSize, gameInfoSize;
    static List<Integer>[] buildings;
    static int[] inDegreeCount;
    static int[] buildCount;
    static boolean isAvailable;

    private static void build(int buildingIdx) {
        //3-1. 아직 지을 수 없는 건물이라면 치트키 사용으로 판단
        if (inDegreeCount[buildingIdx] > 0) {
            isAvailable = false;
            return;
        }

        //3-2. 해당 건물이 처음으로 지어졌다면 영향을 미치는 건물의 영향 제거
        if (++buildCount[buildingIdx] == 1) {
            for (int next : buildings[buildingIdx]) {
                inDegreeCount[next]--;
            }
        }
    }

    private static void destroy(int buildingIdx) {
        //4-1. 현재 지어지지 않은 건물이라면 치트키 사용으로 판단
        if (--buildCount[buildingIdx] < 0) {
            isAvailable = false;
            return;
        }

        //4-2. 해당 건물이 더 이상 존재하지 않는다면 영향력 추가
        if (buildCount[buildingIdx] == 0) {
            for (int next : buildings[buildingIdx]) {
                inDegreeCount[next]++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        input = new BufferedReader(new InputStreamReader(System.in));

        //1. 초기 세팅
        init();

        //2. 게임 정보의 개수만큼 정보 입력 후 처리
        for (int idx = 0; idx < gameInfoSize; idx++) {
            st = new StringTokenizer(input.readLine());
            switch (st.nextToken()) {
                //3. 건물 건설 처리
                case BUILD:
                    build(Integer.parseInt(st.nextToken()));
                    break;
                //4. 건물 파괴 처리
                case DESTROY:
                    destroy(Integer.parseInt(st.nextToken()));
            }

            if (!isAvailable)
                break;
        }

        System.out.println(isAvailable ? "King-God-Emperor" : "Lier!");
    }

    private static void init() throws IOException {
        //1-1. 건물의 개수, 건물 사이의 관계의 개수, 게임 정보의 개수 입력
        st = new StringTokenizer(input.readLine());
        buildingSize = Integer.parseInt(st.nextToken());
        preBuildSize = Integer.parseInt(st.nextToken());
        gameInfoSize = Integer.parseInt(st.nextToken());

        //1-2. 건물의 관계의 개수만큼 관계 정보 입력
        buildings = new ArrayList[buildingSize + 1];
        for (int idx = 1; idx <= buildingSize; idx++) {
            buildings[idx] = new ArrayList<>();
        }
        inDegreeCount = new int[buildingSize + 1];

        for (int idx = 0; idx < preBuildSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            buildings[from].add(to);
            inDegreeCount[to]++;
        }

        //1-3. 변수 초기화
        buildCount = new int[buildingSize + 1];
        isAvailable = true;
    }
}
