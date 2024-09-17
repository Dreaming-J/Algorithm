/*
= BOJ 9370. 미확인 도착지

= 로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
    1-1. 교차로, 도로, 목적지 후보의 개수 입력
    1-2. 출발지, 지난 도로의 교차로 입력
    1-3. 양방향 도로 입력
    1-4. 목적지 후보 입력
2. 최단 거리 찾기
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int INF = 2_000_000;
    static int intersectionSize, roadSize, candidateSize;
    static int start;
    static int[] targetRoad;
    static List<Intersection>[] graph;
    static int[] candidates;
    static Intersection[] distances;

    public static void findMinDist() {
        boolean[] visited = new boolean[intersectionSize + 1];
        PriorityQueue<Intersection> pq = new PriorityQueue<>();

        distances[start] = new Intersection(start, 0, false);
        pq.add(distances[start]);

        while (!pq.isEmpty()) {
            Intersection cur = pq.poll();

            //방문했다면 패스
            if (visited[cur.to])
                continue;
            visited[cur.to] = true;

            //다음 교차로 선택
            for (Intersection next : graph[cur.to]) {
                //현재 위치까지의 최단 거리 + 현재 거리로 현재 선택의 거리 계산
                int distance = cur.distance + next.distance;
                //현재 목표 도로이거나 지금까지의 경로에서 목표 도로를 사용했는지 파악
                boolean useTargetRoad =
                        isTargetRoad(cur.to, next.to) | cur.useTargetRoad;

                if (distance <= distances[next.to].distance) {
                    //기록된 최단 거리와 같다면 목표 도로를 사용했는지 공유
                    if (distance == distances[next.to].distance)
                        useTargetRoad |= distances[next.to].useTargetRoad;
                    
                    distances[next.to] = new Intersection(next.to, distance, useTargetRoad);
                    pq.add(distances[next.to]);
                }
            }
        }
    }

    public static boolean isTargetRoad(int cur, int next) {
        return (targetRoad[0] == cur && targetRoad[1] == next) || (targetRoad[1] == cur && targetRoad[0] == next);
    }

    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 개수 입력
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 0; tc < testCase; tc++) {
            //1. 초기 세팅
            init();

            //2. 최단 거리 찾기
            findMinDist();

            //3. 출력
            for (int candidate : candidates) {
                if (distances[candidate].useTargetRoad) {
                    output.append(distances[candidate].to)
                            .append(" ");
                }
            }
            output.append("\n");
        }
        System.out.println(output);
    }

    public static class Intersection implements Comparable<Intersection> {
        int to, distance;
        boolean useTargetRoad;

        public Intersection(int to, int distance, boolean useTargetRoad) {
            this.to = to;
            this.distance = distance;
            this.useTargetRoad = useTargetRoad;
        }

        @Override
        public int compareTo(Intersection o) {
            //거리가 다르다면 오름차순으로 정렬
            if (distance != o.distance)
                return distance - o.distance;

            //목표 도로를 사용한 경로순으로 정렬
            if (useTargetRoad != o.useTargetRoad)
                return useTargetRoad ? -1 : 1;

            //거리도 같고, 목표 도로 사용여부도 같다면 동일 취급
            return 0;
        }
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 교차로, 도로, 목적지 후보의 개수 입력
        st = new StringTokenizer(input.readLine());
        intersectionSize = Integer.parseInt(st.nextToken());
        roadSize = Integer.parseInt(st.nextToken());
        candidateSize = Integer.parseInt(st.nextToken());

        //1-2. 출발지, 지난 도로의 교차로 입력
        st = new StringTokenizer(input.readLine());
        start = Integer.parseInt(st.nextToken());

        targetRoad = new int[2];
        targetRoad[0] = Integer.parseInt(st.nextToken());
        targetRoad[1] = Integer.parseInt(st.nextToken());

        //1-3. 양방향 도로 입력
        graph = new ArrayList[intersectionSize + 1];
        for (int idx = 1; idx <= intersectionSize; idx++) {
            graph[idx] = new ArrayList<>();
        }

        for (int idx = 0; idx < roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());

            graph[from].add(new Intersection(to, distance, false));
            graph[to].add(new Intersection(from, distance, false));
        }

        //1-4. 목적지 후보 입력
        candidates = new int[candidateSize];
        for (int idx = 0; idx < candidateSize; idx++) {
            candidates[idx] = Integer.parseInt(input.readLine());
        }
        Arrays.sort(candidates);

        //1-5. 변수 초기화
        distances = new Intersection[intersectionSize + 1];
        for (int idx = 1; idx <= intersectionSize; idx++) {
            distances[idx] = new Intersection(idx, INF, false);
        }
    }
}
