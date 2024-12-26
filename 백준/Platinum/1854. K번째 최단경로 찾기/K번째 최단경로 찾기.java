/*
= BOJ 1854. K번째 최단경로 찾기

- 특이사항
다익스트라 알고리즘의 특성 상 우선순위 큐에서 꺼내진 경로는 항상 최단 경로이다.
따라서, 우선순위 큐에서 꺼내진 횟수를 기록하여 K번째 최단경로를 구해보자.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int citySize, roadSize, rank;
    static List<City>[] cities;
    static PriorityQueue<Integer>[] elapsedTime;

    static class City {
        int num, time;

        public City(int num, int time) {
            this.num = num;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findPath();

        printAnswer();
    }

    private static void findPath() {
        PriorityQueue<City> pathes = new PriorityQueue<>((o1, o2) -> o1.time - o2.time);

        pathes.add(new City(1, 0));

        while (!pathes.isEmpty()) {
            City cur = pathes.poll();

            if (elapsedTime[cur.num].peek() < cur.time)
                continue;

            for (City next : cities[cur.num]) {
                int time = cur.time + next.time;

                //경로의 수가 rank라면 기존에 들어있는 가장 오래 걸리는 시간과 비교 후 삽입 여부 판단
                //(elapsedTime은 내림차순 정렬을 했으므로 첫번째 값이 가장 오래 걸리는 시간이다.)
                if (elapsedTime[next.num].size() == rank) {
                    if (time >= elapsedTime[next.num].peek())
                        continue;

                    elapsedTime[next.num].poll();
                }

                elapsedTime[next.num].add(time);
                pathes.add(new City(next.num, time));
            }
        }
    }

    private static void printAnswer() {
        for (int idx = 1; idx <= citySize; idx++)
            output.append(elapsedTime[idx].size() == rank ? elapsedTime[idx].peek().intValue() : -1)
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

        elapsedTime = new PriorityQueue[citySize + 1];
        for (int idx = 1; idx <= citySize; idx++)
            elapsedTime[idx] = new PriorityQueue<>(Collections.reverseOrder());
        elapsedTime[1].add(0);
    }
}