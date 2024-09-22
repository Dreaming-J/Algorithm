/*
= BOJ 1045. 도로
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int spaceGodSize, alreadyConnectedSize;
    static Point[] points;
    static PriorityQueue<Tunnel> tunnels;
    static int[] parents, rank;
    static int connectedSize;
    static double length;

    public static void selectTunnel() {
        //최소 신장 트리 만들기
        while (!tunnels.isEmpty()) {
            Tunnel cur = tunnels.poll();

            if (union(cur.start, cur.end)) {
                length += cur.cost;

                if (++connectedSize == spaceGodSize - 1)
                    break;
            }
        }
    }

    static Point[] selectedPoints;
    public static void makeTunnel(int pointIdx, int selectCount) {
        if (selectCount == 2) {
            if (find(selectedPoints[0].num) != find(selectedPoints[1].num))
                tunnels.add(new Tunnel(selectedPoints[0], selectedPoints[1]));

            return;
        }

        if (pointIdx == spaceGodSize)
            return;

        selectedPoints[selectCount] = points[pointIdx];
        makeTunnel(pointIdx + 1, selectCount + 1);
        makeTunnel(pointIdx + 1, selectCount);
    }

    public static void main(String[] args) throws IOException {
        //초기 세팅
        init();

        //터널 생성
        makeTunnel(0, 0);

        //도로 선택
        selectTunnel();

        //출력
        System.out.printf("%.2f", length);
    }

    public static class Tunnel implements Comparable<Tunnel> {
        int start, end;
        double cost;

        public Tunnel(Point p1, Point p2) {
            this.start = p1.num;
            this.end = p2.num;
            this.cost = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        }

        @Override
        public int compareTo(Tunnel o) {
            return Double.compare(cost, o.cost);
        }
    }

    public static class Point {
        int num, x, y;

        public Point(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
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
        if (parents[element] == 0)
            return element;

        return parents[element] = find(parents[element]);
    }

    public static void init() throws IOException {
        //도시의 수, 도로의 수 입력
        st = new StringTokenizer(input.readLine());
        spaceGodSize = Integer.parseInt(st.nextToken());
        alreadyConnectedSize = Integer.parseInt(st.nextToken());

        //도로 정보 입력
        points = new Point[spaceGodSize];
        for (int idx = 0; idx < spaceGodSize; idx++) {
            st = new StringTokenizer(input.readLine());
            points[idx] = new Point(idx + 1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        //연결된 통로 입력
        parents = new int[spaceGodSize + 1];
        rank = new int[spaceGodSize + 1];
        for (int idx = 0; idx < alreadyConnectedSize; idx++) {
            st = new StringTokenizer(input.readLine());
            if (union(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())))
                connectedSize++;
        }

        //변수 초기화
        tunnels = new PriorityQueue<>();
        selectedPoints = new Point[2];
    }
}