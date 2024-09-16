/*
= BOJ 1516. 게임 개발

= 로직
1. 초기 세팅
    1-1. 건물 개수 입력
    1-2. 건물의 건설 시간 및 선행 건물 입력
2. 위상 정렬
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int buildingSize;
    static List<Integer>[] buildings;
    static int[] inDegree, time, buildTime;

    public static void topologicalSort() {
        Deque<Integer> queue = new ArrayDeque<>();

        for (int idx = 1; idx <= buildingSize; idx++) {
            if (inDegree[idx] == 0) {
                buildTime[idx] = time[idx];
                queue.add(idx);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int next : buildings[cur]) {
                buildTime[next] = Math.max(buildTime[cur] + time[next], buildTime[next]);

                if (--inDegree[next] == 0) {
                    time[next] += time[cur];
                    queue.add(next);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 위상 정렬
        topologicalSort();

        //3. 출력
        for (int idx = 1; idx <= buildingSize; idx++) {
            output.append(buildTime[idx])
                    .append("\n");
        }
        System.out.print(output);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 건물 개수 입력
        buildingSize = Integer.parseInt(input.readLine());

        //1-2. 건물의 건설 시간 및 선행 건물 입력
        buildings = new ArrayList[buildingSize + 1];
        for (int idx = 1; idx <= buildingSize; idx++) {
            buildings[idx] = new ArrayList<>();
        }
        inDegree = new int[buildingSize + 1];
        time = new int[buildingSize + 1];

        for (int idx = 1; idx <= buildingSize; idx++) {
            st = new StringTokenizer(input.readLine());

            time[idx] = Integer.parseInt(st.nextToken());

            int token;
            while ((token = Integer.parseInt(st.nextToken())) != -1) {
                buildings[token].add(idx);
                inDegree[idx]++;
            }
        }

        buildTime = new int[buildingSize + 1];
    }
}
