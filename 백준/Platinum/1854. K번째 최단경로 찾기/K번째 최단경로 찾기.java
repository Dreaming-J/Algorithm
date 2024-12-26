/*
= BOJ 1854. K번째 최단경로 찾기

- 특이사항
다익스트라 알고리즘의 특성 상 우선순위 큐에서 꺼내진 경로는 항상 최단 경로이다.
따라서, 우선순위 큐에서 꺼내진 횟수를 기록하여 K번째 최단경로를 구해보자.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_TIME = 1_000_000;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int citySize, roadSize, rank;
    static List<City>[] cities;
    static Time[] elapsedTime;

    static class City {
        int num, time;

        public City(int num, int time) {
            this.num = num;
            this.time = time;
        }
    }

    static class Time {
        int time, count;

        public Time(int time, int count) {
            this.time = time;
            this.count = count;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findPath();

        printAnswer();
    }

    private static void findPath() {
        PriorityQueue<City> pq = new PriorityQueue<>((o1, o2) -> o1.time - o2.time);

        pq.add(new City(1, 0));

        while (!pq.isEmpty()) {
            City cur = pq.poll();

            if (elapsedTime[cur.num].count == rank)
                continue;

            elapsedTime[cur.num].time = cur.time;
            elapsedTime[cur.num].count++;

            for (City next : cities[cur.num]) {
                pq.add(new City(next.num, cur.time + next.time));
            }
        }
    }

    private static void printAnswer() {
        for (int idx = 1; idx <= citySize; idx++)
            output.append(elapsedTime[idx].count == rank ? elapsedTime[idx].time : -1)
                    .append("\n");

        System.out.println(output);
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        citySize = Integer.parseInt(st.nextToken());
        roadSize = Integer.parseInt(st.nextToken());
        rank = Integer.parseInt(st.nextToken());

        cities = new ArrayList[citySize + 1];
        for (int idx = 1; idx <= citySize; idx++)
            cities[idx] = new ArrayList<>();

        for (int idx = 0; idx < roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            cities[from].add(new City(to, time));
        }

        elapsedTime = new Time[citySize + 1];
        for (int idx = 1; idx <= citySize; idx++)
            elapsedTime[idx] = new Time(MAX_TIME, 0);
    }
}