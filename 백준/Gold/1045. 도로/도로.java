/*
= BOJ 1045. 도로
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int citySize, selectedRoadSize;
    static PriorityQueue<Road> roads, abandonedRoads;
    static int[] parents, rank;
    static int selectedRoadCount;
    static int[] selectedRoads;

    public static void selectRoad() {
        //최소 신장 트리 만들기
        while (!roads.isEmpty()) {
            Road cur = roads.poll();

            if (union(cur.start, cur.end)) {
                selectedRoads[cur.start]++;
                selectedRoads[cur.end]++;

                if (++selectedRoadCount == citySize - 1)
                    break;
            } else
                abandonedRoads.add(cur);
        }

        //선택할 도로 개수와 같거나 MST를 형성하지 못했다면 종료
        if (selectedRoadCount == selectedRoadSize || selectedRoadCount < citySize - 1)
            return;

        //버려진 도로 다시 추가
        roads.addAll(abandonedRoads);

        //남은 도로 마저 선택하기
        while (!roads.isEmpty()) {
            Road cur = roads.poll();

            selectedRoads[cur.start]++;
            selectedRoads[cur.end]++;
            if (++selectedRoadCount == selectedRoadSize)
                return;
        }
    }

    public static void main(String[] args) throws IOException {
        //초기 세팅
        init();

        //도로 선택
        selectRoad();

        //출력
        if (selectedRoadCount >= selectedRoadSize) {
            for (int selectedRoad : selectedRoads)
                output.append(selectedRoad)
                        .append(" ");
        } else
            output.append(-1);
        System.out.println(output);
    }

    public static class Road implements Comparable<Road> {
        int start, end;

        public Road(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Road o) {
            return start == o.start ? end - o.end : start - o.start;
        }
    }

    public static boolean union(int element1, int element2) {
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

    public static int find(int element) {
        if (parents[element] == element)
            return element;

        return parents[element] = find(parents[element]);
    }

    public static void init() throws IOException {
        //도시의 수, 도로의 수 입력
        st = new StringTokenizer(input.readLine());
        citySize = Integer.parseInt(st.nextToken());
        selectedRoadSize = Integer.parseInt(st.nextToken());

        //도로 정보 입력
        roads = new PriorityQueue<>();
        for (int start = 0; start < citySize; start++) {
            String line = input.readLine();
            for (int end = start + 1; end < citySize; end++) {
                if (line.charAt(end) == 'N')
                    continue;

                roads.add(new Road(start, end));
            }
        }

        //변수 초기화
        abandonedRoads = new PriorityQueue<>();
        selectedRoads = new int[citySize];

        parents = new int[citySize];
        Arrays.setAll(parents, idx -> idx);
        rank = new int[citySize];
    }
}