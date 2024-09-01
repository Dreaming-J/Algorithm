/*
= BOJ 2887. 행성 터널

= 특이사항
최소 스패닝 트리

= 로직
1. 초기 세팅
    1-1. 행성의 개수 입력
    1-2. 행성의 개수만큼 3차원 좌표 입력
    1-3. 변수 초기화
2. 행성간 모든 터널 생성
    2-1. x축을 기준으로 정렬했을 때, 양 옆 행성 간의 터널 생성
    2-2. y축을 기준으로 정렬했을 때, 양 옆 행성 간의 터널 생성
    2-3. z축을 기준으로 정렬했을 때, 양 옆 행성 간의 터널 생성
3. 최소 비용인 터널 선택
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input;
    static StringTokenizer st;
    static int planetSize;
    static Planet[] planets;
    static PriorityQueue<Tunnel> tunnels;
    static int[] parents, rank;
    static int minCost;

    private static void makeTunnel() {
        tunnels = new PriorityQueue<>();

        //2-1. x축을 기준으로 정렬했을 때, 양 옆 행성 간의 터널 생성
        Arrays.sort(planets, (o1, o2) -> Integer.compare(o1.x, o2.x));
        for (int idx = 0; idx < planetSize - 1; idx++) {
            tunnels.add(
                    new Tunnel(planets[idx].num, planets[idx + 1].num, Math.abs(planets[idx].x - planets[idx + 1].x)));
        }

        //2-2. y축을 기준으로 정렬했을 때, 양 옆 행성 간의 터널 생성
        Arrays.sort(planets, (o1, o2) -> Integer.compare(o1.y, o2.y));
        for (int idx = 0; idx < planetSize - 1; idx++) {
            tunnels.add(
                    new Tunnel(planets[idx].num, planets[idx + 1].num, Math.abs(planets[idx].y - planets[idx + 1].y)));
        }

        //2-3. y축을 기준으로 정렬했을 때, 양 옆 행성 간의 터널 생성
        Arrays.sort(planets, (o1, o2) -> Integer.compare(o1.z, o2.z));
        for (int idx = 0; idx < planetSize - 1; idx++) {
            tunnels.add(
                    new Tunnel(planets[idx].num, planets[idx + 1].num, Math.abs(planets[idx].z - planets[idx + 1].z)));
        }
    }

    private static void chooseTunnelForMinCost() {
        int cnt = 0;
        while (!tunnels.isEmpty()) {
            Tunnel cur = tunnels.poll();

            if (union(cur.start, cur.end)) {
                minCost += cur.cost;

                if (++cnt == planetSize - 1)
                    return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        input = new BufferedReader(new InputStreamReader(System.in));

        //1. 초기 세팅
        init();

        //2. 행성간 터널 생성
        makeTunnel();

        //3. 최소 비용인 터널 선택
        chooseTunnelForMinCost();

        //4. 출력
        System.out.println(minCost);
    }

    private static class Tunnel implements Comparable<Tunnel> {
        int start, end, cost;

        public Tunnel(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Tunnel o) {
            return Integer.compare(cost, o.cost);
        }
    }

    private static class Planet {
        int num, x, y, z;

        public Planet(int num, int x, int y, int z) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static void init() throws IOException {
        //1-1. 행성의 개수 입력
        planetSize = Integer.parseInt(input.readLine());

        //1-2. 행성의 개수만큼 3차원 좌표 입력
        planets = new Planet[planetSize];
        for (int idx = 0; idx < planetSize; idx++) {
            st = new StringTokenizer(input.readLine());
            planets[idx] = new Planet(idx, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()));
        }

        //1-3. 변수 초기화
        parents = new int[planetSize];
        Arrays.setAll(parents, idx -> idx);

        rank = new int[planetSize];
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
        if (parents[element] == element)
            return element;

        return parents[element] = find(parents[element]);
    }
}
