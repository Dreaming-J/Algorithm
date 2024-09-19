/*
= BOJ 2307. 도로검문
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int INF = 10_000_000, START = 1;
    static int citySize, roadSize;
    static List<City>[] graph;
    static int[] minTime;
    static Deque<Integer> minPath;
    static int normalTime, delayTime;

    //검문이 없을 때의 최단 시간 탐색
    public static void findMinTimeWithNoCheck() {
        boolean[] visited = new boolean[citySize + 1];
        PriorityQueue<City> pq = new PriorityQueue<>();

        //최단시간 배열 초기화
        initMinTimeArray();

        pq.add(new City(0, START, 0));
        while (!pq.isEmpty()) {
            City cur = pq.poll();

            //방문했다면 패스
            if (visited[cur.num])
                continue;
            visited[cur.num] = true;

            //최단경로에 저장
            minPath.add(cur.roadIdx);

            for (City next : graph[cur.num]) {
                int nextTime = cur.time + next.time;

                if (nextTime < minTime[next.num]) {
                    minTime[next.num] = nextTime;
                    pq.add(new City(next.roadIdx, next.num, nextTime));
                }
            }
        }

        //처음 추가된 최단경로는 패스
        minPath.pop();

        //검문이 없을 때의 최단시간 저장
        normalTime = minTime[citySize];
    }

    //최단경로에 사용된 도로를 하나씩 막으며 지연 시간 탐색
    public static void findMinTimeWithCheck(int checkRoadIdx) {
        boolean[] visited = new boolean[citySize + 1];
        PriorityQueue<City> pq = new PriorityQueue<>();

        //최단시간 배열 초기화
        initMinTimeArray();

        pq.add(new City(0, START, 0));
        while (!pq.isEmpty()) {
            City cur = pq.poll();

            //방문했다면 패스
            if (visited[cur.num])
                continue;
            visited[cur.num] = true;

            for (City next : graph[cur.num]) {
                //검문 중인 도로라면 패스
                if (next.roadIdx == checkRoadIdx)
                    continue;

                int nextTime = cur.time + next.time;

                if (nextTime < minTime[next.num]) {
                    minTime[next.num] = nextTime;
                    pq.add(new City(next.roadIdx, next.num, nextTime));
                }
            }
        }

        //지연시간 갱신
        delayTime = Math.max(minTime[citySize] - normalTime, delayTime);
    }


    public static void main(String[] args) throws IOException {
        //초기 세팅
        init();

        //검문이 없을 때의 최단 시간 탐색
        findMinTimeWithNoCheck();

        //최단경로에 사용된 도로를 하나씩 막으며 지연 시간 탐색
        while (!minPath.isEmpty())
            findMinTimeWithCheck(minPath.poll());

        //출력
        System.out.print(delayTime + normalTime == INF ? -1 : delayTime);
    }

    //최단시간 배열 초기화
    public static void initMinTimeArray() {
        minTime[START] = 0;
        for (int idx = 2; idx <= citySize; idx++)
            minTime[idx] = INF;
    }

    public static class City implements Comparable<City> {
        int roadIdx, num, time;

        public City(int roadIdx, int num, int time) {
            this.roadIdx = roadIdx;
            this.num = num;
            this.time = time;
        }

        @Override
        public int compareTo(City o) {
            return time - o.time;
        }
    }

    //초기 세팅
    public static void init() throws IOException {
        //도시의 개수, 도로의 개수 입력
        st = new StringTokenizer(input.readLine());
        citySize = Integer.parseInt(st.nextToken());
        roadSize = Integer.parseInt(st.nextToken());

        //그래프 초기화
        graph = new ArrayList[citySize + 1];
        for (int idx = 1; idx <= citySize; idx++)
            graph[idx] = new ArrayList<>();

        //도로 정보 입력
        for (int idx = 1; idx <= roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            graph[from].add(new City(idx, to, time));
            graph[to].add(new City(idx, from, time));
        }

        //변수 초기화
        minTime = new int[citySize + 1];
        minPath = new ArrayDeque<>();
    }
}