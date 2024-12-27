/*
= BOJ 32011. Of the Children

- 특이사항

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int END_OF_INPUT = 0;
    static final String END_OF_EDGE_INPUT = "-1 -1 -1";
    private static final int MAX_TRAVEL_COST = 10_000;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int citySize;
    static int[] donations;
    static List<List<City>> cities;
    static int maxFunding;

    public static void main(String[] args) throws IOException {
        while (init()) {
            if (canReachDestination())
                output.append(findMinFunding())
                        .append("\n");
            else
                output.append(-1)
                        .append("\n");
        }

        System.out.println(output);
    }

    private static boolean canReachDestination() {
        boolean[] visited = new boolean[citySize];
        Deque<Integer> queue = new ArrayDeque<>();

        visited[0] = true;
        queue.add(0);

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            //목적지에 도착했다면 탐색 종료
            if (cur == citySize - 1)
                return true;

            //다음 도시로 이동
            for (City next : cities.get(cur)) {
                //이미 방문한 도시라면
                if (visited[next.num])
                    continue;

                visited[next.num] = true;
                queue.add(next.num);
            }
        }

        return false;
    }

    private static int findMinFunding() {
        int left = 1;
        int right = maxFunding;

        while (left < right) {
            int mid = (left + right) / 2;

            if (travelWithFunding(0, mid, 1))
                right = mid;
            else
                left = mid + 1;
        }

        return left;
    }

    private static boolean travelWithFunding(int cur, int funding, int visitedBit) {
        //목적지에 도착했다면 탐색 종료
        if (cur == citySize - 1)
            return true;

        //후원금 받기
        funding += donations[cur];

        //다음 도시로 이동
        boolean canTravel = false;
        for (City next : cities.get(cur)) {
            //이미 목적지에 도착했다면
            if (canTravel)
                break;

            //해당 도시를 방문했다면
            if ((visitedBit & 1 << next.num) != 0)
                continue;

            //교통비가 부족하다면
            if (funding < next.cost)
                continue;

            canTravel = travelWithFunding(next.num, funding - next.cost, visitedBit | 1 << next.num);
        }

        return canTravel;
    }

    private static boolean init() throws IOException {
        citySize = Integer.parseInt(input.readLine());

        if (citySize == END_OF_INPUT)
            return false;

        donations = new int[citySize];
        for (int idx = 1; idx < citySize - 1; idx++)
            donations[idx] = Integer.parseInt(input.readLine());

        cities = new ArrayList<>();
        for (int idx = 0; idx < citySize; idx++)
            cities.add(new ArrayList<>());

        String edge = null;
        while (!(edge = input.readLine()).equals(END_OF_EDGE_INPUT)) {
            st = new StringTokenizer(edge);
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            cities.get(from).add(new City(to, cost));
            cities.get(to).add(new City(from, cost));
        }

        maxFunding = citySize * MAX_TRAVEL_COST;

        return true;
    }

    static class City {
        int num, cost;

        public City(int num, int cost) {
            this.num = num;
            this.cost = cost;
        }
    }
}