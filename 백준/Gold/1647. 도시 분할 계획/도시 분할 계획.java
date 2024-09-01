/*
= BOJ 2887. 행성 터널

= 특이사항
최소 스패닝 트리
집의 번호는 1부터 시작

= 로직
1. 초기 세팅
    1-1. 집의 개수, 길의 개수 입력
    1-2. 길의 개수만큼 길의 정보 입력
    1-3. 변수 초기화
2. 최소 비용인 길을 (집의 개수 - 2)개만큼 선택
    2-1. 집의 개수가 2개인 경우, 고를 필요가 없음
    2-2. (집의 개수 - 2)개 고를 때까지, 도로 선택
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input;
    static StringTokenizer st;
    static int houseSize, roadSize;
    static PriorityQueue<Road> roads;
    static int[] parents, rank;
    static int minCost;

    private static void chooseRoadForMinCost() {
        //2-1. 집의 개수가 2개인 경우, 고를 필요가 없음
        if (houseSize == 2)
            return;

        //2-2. (집의 개수 - 2)개 고를 때까지, 도로 선택
        int cnt = 0;
        while (!roads.isEmpty()) {
            Road cur = roads.poll();

            if (union(cur.start, cur.end)) {
                minCost += cur.cost;

                if (++cnt == houseSize - 2)
                    return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        input = new BufferedReader(new InputStreamReader(System.in));

        //1. 초기 세팅
        init();

        //2. 최소 비용인 길을 (집의 개수 - 2)개만큼 선택
        chooseRoadForMinCost();

        //3. 출력
        System.out.println(minCost);
    }

    private static class Road implements Comparable<Road> {
        int start, end, cost;

        public Road(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Road o) {
            return Integer.compare(cost, o.cost);
        }
    }

    private static void init() throws IOException {
        //1-1. 집의 개수, 길의 개수 입력
        st = new StringTokenizer(input.readLine());
        houseSize = Integer.parseInt(st.nextToken());
        roadSize = Integer.parseInt(st.nextToken());

        //1-2. 길의 개수만큼 길의 정보 입력
        roads = new PriorityQueue<>();
        for (int idx = 0; idx < roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            roads.add(new Road(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken())));
        }

        //1-3. 변수 초기화
        parents = new int[houseSize + 1];
        rank = new int[houseSize + 1];
    }

    private static boolean union(int element1, int element2) {
        int parent1 = find(element1);
        int parent2 = find(element2);

        if (parent1 == parent2)
            return false;

        if (rank[parent1] > rank[parent2]) {
            parents[parent2] = parent1;
            return true;
        }

        if (rank[parent1] == rank[parent2])
            rank[parent2]++;

        parents[parent1] = parent2;
        return true;
    }

    private static int find(int element) {
        if (parents[element] == 0)
            return element;

        return parents[element] = find(parents[element]);
    }
}
