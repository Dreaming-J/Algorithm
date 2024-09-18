/*
= BOJ 13911. 집 구하기

= 로직
1. 초기 세팅
    모든 맥도날드와 연결되어 있고 거리가 0인 노드 추가
    모든 스타벅스와 연결되어 있고 거리가 0인 노드 추가
2. 맥도날드 노드로부터 최단거리 찾기
3. 스타벅스 노드로부터 최단거리 찾기
4. 맥세권과 스세권을 만족하면서 거리의 합이 최소인 집 찾기
5. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int MCDONALD = 0, STARBUCKS = 1;
    static List<House>[] graph;
    static int[] startNode;     //맥도날드와 스타벅스 시작 노드 위치
    static int[] conditions;    //맥세권, 스세권이기 위한 최소 거리
    static boolean[][] is;      //해당 집이 맥도날드나 스타벅스인지 파악하는 배열
    static int[][] distances;   //각 집에서의 맥도날드까지의 거리와 스타벅스까지의 거리를 기록하는 배열
    static int answer;

    public static void findMinDist(int place) {
        boolean[] visited = new boolean[houseSize + 1];
        PriorityQueue<House> pq = new PriorityQueue<>();

        pq.add(new House(startNode[place], 0));

        while (!pq.isEmpty()) {
            House cur = pq.poll();

            if (visited[cur.num])
                continue;
            visited[cur.num] = true;

            for (House next : graph[cur.num]) {
                int distance = distances[place][cur.num] + next.weight;

                if (distance < distances[place][next.num]) {
                    distances[place][next.num] = distance;
                    pq.add(new House(next.num, distance));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 맥도날드 노드로부터 최단거리 찾기
        findMinDist(MCDONALD);

        //3. 스타벅스 노드로부터 최단거리 찾기
        findMinDist(STARBUCKS);

        //4. 맥세권과 스세권을 만족하면서 거리의 합이 최소인 집 찾기
        for (int idx = 1; idx <= houseSize; idx++) {
            //맥도날드나 스타벅스인 집은 패스
            if (is[MCDONALD][idx] || is[STARBUCKS][idx])
                continue;
            
            //맥세권 거리보다 크다면 패스
            if (distances[MCDONALD][idx] > conditions[MCDONALD])
                continue;
            
            //스세권 거리보다 크다면 패스
            if (distances[STARBUCKS][idx] > conditions[STARBUCKS])
                continue;
            
            answer = Math.min(distances[MCDONALD][idx] + distances[STARBUCKS][idx], answer);
        }

        //5. 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    public static class House implements Comparable<House> {
        int num, weight;

        public House(int num, int weight) {
            this.num = num;
            this.weight = weight;
        }

        @Override
        public int compareTo(House o) {
            return weight - o.weight;
        }
    }

    //1. 초기 세팅
    static int houseSize, roadSize, mcDonaldSize, starBucksSize;
    public static void init() throws IOException {
        //집과 도로의 개수 입력
        st = new StringTokenizer(input.readLine());
        houseSize = Integer.parseInt(st.nextToken()) + 2; //모든 맥도날드를 잇는 노드와 모든 스타벅스를 잇는 노드 2개 추가
        roadSize = Integer.parseInt(st.nextToken());

        //그래프 초기화
        graph = new ArrayList[houseSize + 1];
        for (int idx = 1; idx <= houseSize; idx++)
            graph[idx] = new ArrayList<>();

        //도로 정보 입력
        for (int idx = 0; idx < roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph[from].add(new House(to, weight));
            graph[to].add(new House(from, weight));
        }

        //맥도날드 노드와 스타벅스 노드 저장
        startNode = new int[2];
        startNode[MCDONALD] = houseSize - 1;
        startNode[STARBUCKS] = houseSize;

        //맥도날드와 스타벅스 정보 입력
        conditions = new int[2];
        is = new boolean[2][houseSize + 1];
        for (int idx = 0; idx < 2; idx++) {
            //가게의 수와 맥세권 조건 입력
            st = new StringTokenizer(input.readLine());
            mcDonaldSize = Integer.parseInt(st.nextToken());
            conditions[idx] = Integer.parseInt(st.nextToken());

            //가게인 집의 번호 입력
            st = new StringTokenizer(input.readLine());
            for (int houseIdx = 0; houseIdx < mcDonaldSize; houseIdx++) {
                int to = Integer.parseInt(st.nextToken());
                is[idx][to] = true;
                graph[startNode[idx]].add(new House(to, 0));
            }
        }

        //변수 초기화
        distances = new int[2][houseSize + 1];
        Arrays.fill(distances[MCDONALD], Integer.MAX_VALUE);
        Arrays.fill(distances[STARBUCKS], Integer.MAX_VALUE);
        distances[MCDONALD][startNode[MCDONALD]] = 0;
        distances[STARBUCKS][startNode[STARBUCKS]] = 0;

        answer = Integer.MAX_VALUE;
    }
}
